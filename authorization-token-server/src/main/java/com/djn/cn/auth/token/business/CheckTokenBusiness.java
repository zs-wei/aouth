package com.djn.cn.auth.token.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import com.djn.cn.auth.token.app.dao.IAppInterfaceDAO;
import com.djn.cn.auth.token.app.dao.IInterfaceInfoDAO;
import com.djn.cn.auth.token.app.dao.ITokenAppDAO;
import com.djn.cn.auth.token.app.dto.AppBlackListDto;
import com.djn.cn.auth.token.app.dto.AppDto;
import com.djn.cn.auth.token.app.dto.AppFlowDto;
import com.djn.cn.auth.token.app.dto.AppInterfaceDto;
import com.djn.cn.auth.token.app.dto.InterfaceDto;
import com.djn.cn.auth.token.base.util.RedisCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 
 * <b>类   名：</b>CheckTokenBusiness<br/>
 * <b>类描述：</b>业务检验<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月17日 下午9:40:41<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月17日 下午9:40:41<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
@Component("checkTokenBusiness")
public class CheckTokenBusiness {
    private static final Logger log = LoggerFactory.getLogger(CheckTokenBusiness.class);
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;
    @Autowired
    private IAppInterfaceDAO iAppInterfaceDAO;
    @Autowired
    private ITokenAppDAO iTokenAppDAO;
    @Autowired
    private IInterfaceInfoDAO iInterfaceInfoDAO;
    
    
//    private static final String ERROR_CODE = "errorCode";
//    private static final String ERROR_MSG = "errorMsg";
//    private static final String DEVELOPER_ID = "developerId";
    public  static final String PLATFORM = "platform";
    
    /**
     * 
     * doCheck  访问check
     *
     * @param token  
     * @param appkey  
     * @param serviceName  
     * @param interfaceName  
     * @param interfaceUrl   
     * @param clientIp   
     * @param response    
     * @return   
     * @since 1.0
     * @author op.nie-dongjia
     */
    public boolean doCheck(String token, String appkey, String serviceName, String interfaceName, String interfaceUrl, String clientIp, Map<String, Object> response)
    {
      if (checkBlackList(appkey, clientIp, response)) {
        return false;
      }
      if (!checkParam(appkey, serviceName, interfaceName, response)) {
        return false;
      }
      appkey = appkey.trim();
      serviceName = serviceName.trim();
      interfaceName = interfaceName.trim();
      if (StringUtils.isBlank(token))
      {
        if (!checkWhiteList(response, appkey)) {
          return false;
        }
        return true;
      }
      token = token.trim();
      if (!checkInterface(serviceName, interfaceName, response, interfaceUrl)) {
        return false;
      }
      if (!checkToken(token, appkey, response)) {
        return false;
      }
      if (!checkInfaceRight(appkey, serviceName, interfaceName, response)) {
        return false;
      }
      if (!checkFlow(appkey, serviceName, interfaceName, response)) {
        return false;
      }
      return true;
    }
    
    private boolean checkBlackList(String appkey, String clientIp, Map<String, Object> response)
    {
      List<AppBlackListDto> list = null;
      try
      {
        list = iTokenAppDAO.queryBlackListByAppKey(appkey);
      }
      catch (Exception e)
      {
        log.error("query app blacklist by key error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        return false;
      }
      if (list == null)
      {
        log.error("app with key:" + appkey + " is not in the blacklist");
        putResponse(response, "check_success", "check_blacklist_success");
        return false;
      }
      if ((list != null) && (list.size() >= 1))
      {
        int i = 0;
        for (int len = list.size(); i < len; i++)
        {
          String ip = ((AppBlackListDto)list.get(i)).getClientIp();
          if ((ip.equals("1")) || (clientIp.equals(ip)))
          {
            log.error("app with key:" + appkey + " is in the blacklist");
            putResponse(response, "check_blacklist_fail", "clientIp:" + clientIp + " is in the blacklist");
            return true;
          }
        }
      }
      return false;
    }
    
    private boolean checkInfaceRight(String appkey, String serviceName, String interfaceName, Map<String, Object> response)
    {
      Map<String, String> map = new HashMap<>();
      map.put("appkey", appkey);
      map.put("interfaceName", interfaceName);
      map.put("serviceName", serviceName);
      List<AppInterfaceDto> appInterfaceDtos = null;
      try
      {
        appInterfaceDtos = iAppInterfaceDAO.queryByAll(map);
      }
      catch (Exception e)
      {
        log.error("query interface error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        return false;
      }
      if ((appInterfaceDtos != null) && (appInterfaceDtos.size() == 1))
      {
        log.info("check_success");
        response.put("errorCode", "check_success");
        response.put("developerId", ((AppInterfaceDto)appInterfaceDtos.get(0)).getDeveloperId());
        response.put("platform", ((AppInterfaceDto)appInterfaceDtos.get(0)).getPlatform());
      }
      else
      {
        log.error("AppInterfaceDto size from db is not 1");
        putResponse(response, "check_interface_fail", "check_interface_fail");
        return false;
      }
      return true;
    }
    
    private boolean checkFlow(String appKey, String serviceName, String interfaceName, Map<String, Object> response)
    {
      Jedis jedis = this.jedisPool.getResource();
      
      AppFlowDto flowMax = null;
      try
      {
        flowMax = iTokenAppDAO.queryFlowMax(appKey, serviceName, interfaceName);
      }
      catch (Exception e)
      {
        log.error("query interface max flow error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        jedis.close();
        return false;
      }
      String resourceService = this.iTokenAppDAO.queryResourceServiceByName(serviceName);
      String flowSumKey = "flow:sum:" + appKey + ":" + resourceService + ":" + interfaceName;
      String flowCurDayKey = "flow:daily:" + appKey + ":" + resourceService + ":" + interfaceName;
      
      String sumFlow = jedis.get(flowSumKey);
      String dailyFlow = jedis.get(flowCurDayKey);
      if ((StringUtils.isBlank(sumFlow)) || (StringUtils.isBlank(dailyFlow)))
      {
        List<String> dailyFlowKeys = new ArrayList<>();
        dailyFlowKeys.add(flowCurDayKey);
        redisCache.set("dailyFlowKeys", dailyFlowKeys);
      }
      if (flowMax == null)
      {
        jedis.incr(flowSumKey);
        jedis.incr(flowCurDayKey);
        putResponse(response, "check_success", "check_interface_flow_success");
        jedis.close();
        return true;
      }
      String flowAllStr = jedis.get(flowSumKey);
      Integer flowAllSum = Integer.valueOf(StringUtils.isBlank(flowAllStr) ? 0 : Integer.parseInt(flowAllStr));
      if (flowMax.getSum() == null)
      {
        jedis.incr(flowSumKey);
      }
      else
      {
        if (flowAllSum.intValue() > flowMax.getSum().intValue())
        {
          log.error("interface flow exceed the max value of total");
          putResponse(response, "check_flow_fail", "sum overflow");
          jedis.close();
          return false;
        }
        jedis.incr(flowSumKey);
      }
      String dailyStr = jedis.get(flowCurDayKey);
      Integer dailySum = Integer.valueOf(StringUtils.isBlank(dailyStr) ? 0 : Integer.parseInt(dailyStr));
      if (flowMax.getDailySum() == null)
      {
        jedis.incr(flowCurDayKey);
        putResponse(response, "check_success", "check_interface_flow_success");
        jedis.close();
        return true;
      }
      if (dailySum.intValue() >= flowMax.getDailySum().intValue())
      {
        log.error("interface flow exceed the max value of current day");
        putResponse(response, "check_flow_fail", "dailySum overflow");
        jedis.close();
        return false;
      }
      jedis.incr(flowCurDayKey);
      putResponse(response, "check_success", "check_interface_flow_success");
      jedis.close();
      return true;
    }
    
    private boolean checkToken(String token, String appkey, Map<String, Object> response)
    {
      OAuth2AccessToken otoken = null;
      OAuth2Authentication auth2Authentication = null;
      try
      {
        otoken = this.resourceServerTokenServices.readAccessToken(token);
        auth2Authentication = this.resourceServerTokenServices.loadAuthentication(token);
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        if (!appkey.equals(clientId))
        {
          log.error("##########the token is not from app");
          putResponse(response, "invalid_token", "Token was not recognised");
          return false;
        }
        if (otoken.isExpired())
        {
          log.error("Token has expired");
          putResponse(response, "invalid_token_expired", "Token has expired");
          return false;
        }
      }
      catch (Exception e)
      {
        log.error("read access token error", e);
        putResponse(response, "invalid_token", "readAccessToken fail");
        return false;
      }
      return true;
    }
    
    private boolean checkInterface(String serviceName, String interfaceName, Map<String, Object> response, String interfaceUrl)
    {
      List<InterfaceDto> interfaceDtos = null;
      try
      {
        Map<String, String> map = new HashMap<>();
        map.put("serviceName", serviceName);
        map.put("interfaceName", interfaceName);
        interfaceDtos = this.iInterfaceInfoDAO.queryByName(map);
      }
      catch (Exception e)
      {
        log.error("query interface error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        return false;
      }
      if ((interfaceDtos != null) && (interfaceDtos.size() == 1))
      {
        String urlInDb = ((InterfaceDto)interfaceDtos.get(0)).getUrl();
        if ((StringUtils.isBlank(urlInDb)) || (!urlInDb.equals(interfaceUrl)))
        {
          log.error("StringUtils.isBlank(urlInDb) || !urlInDb.equals(interfaceUrl)");
          putResponse(response, "check_interface_fail", "check_interface_fail");
          return false;
        }
      }
      else
      {
        log.error("interface with name " + interfaceName + " is not exit or duplicate");
        putResponse(response, "check_interface_fail", "check_interface_fail");
        return false;
      }
      return true;
    }
    
    private boolean checkParam(String appkey, String serviceName, String interfaceName, Map<String, Object> response)
    {
      if ((StringUtils.isBlank(appkey)) || 
        (StringUtils.isBlank(serviceName)) || 
        (StringUtils.isBlank(interfaceName)))
      {
        log.error("appkey or serviceName or interfaceName is null");
        putResponse(response, "check_param_fail", "appkey or serviceName or interfaceName is null");
        return false;
      }
      return true;
    }
    
    private boolean checkWhiteList(Map<String, Object> response, String appkey)
    {
      List<AppDto> list = null;
      try
      {
        list = this.iTokenAppDAO.queryByAppkey(appkey);
      }
      catch (Exception e)
      {
        log.error("query app by key error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        return false;
      }
      if ((list == null) || (list.size() != 1))
      {
        log.error("app with key:" + appkey + " count error");
        putResponse(response, "check_app_fail", "invalid app");
        return false;
      }
      return true;
    }
    
    public boolean doCheckTokenOnly(String token, String appkey, String clientIp, Map<String, Object> response)
    {
      if (checkBlackList(appkey, clientIp, response)) {
        return false;
      }
      if (StringUtils.isBlank(token))
      {
        log.error("doCheckTokenOnly token is null ");
        putResponse(response, "invalid_token", "Token was not recognised");
        return false;
      }
      token = token.trim();
      if (StringUtils.isBlank(appkey))
      {
        log.error("doCheckTokenOnly appkey is null ");
        putResponse(response, "invalid_app", "invalid app");
        return false;
      }
      appkey = appkey.trim();
      
      List<AppDto> list = null;
      try
      {
        list = this.iTokenAppDAO.queryByAppkey(appkey);
      }
      catch (Exception e)
      {
        log.error("query app by key error", e);
        putResponse(response, "query_db_fail", "query_db_fail");
        return false;
      }
      if ((list == null) || (list.size() != 1))
      {
        log.error("app with key:" + appkey + " count error");
        putResponse(response, "invalid_app", "invalid app");
        return false;
      }
      OAuth2AccessToken otoken = null;
      OAuth2Authentication auth2Authentication = null;
      try
      {
        otoken = this.resourceServerTokenServices.readAccessToken(token);
        auth2Authentication = this.resourceServerTokenServices.loadAuthentication(token);
        String clientId = auth2Authentication.getOAuth2Request().getClientId();
        if (!appkey.equals(clientId))
        {
          log.error("##########the token is not from app");
          putResponse(response, "invalid_token", "Token was not recognised");
          return false;
        }
        if (otoken.isExpired())
        {
          log.error("Token has expired");
          putResponse(response, "invalid_token_expired", "Token has expired");
          return false;
        }
      }
      catch (Exception e)
      {
        log.error("read access token error", e);
        putResponse(response, "invalid_token", "Token was not recognised");
        return false;
      }
      response.put("errorCode", "check_success");
      return true;
    }
    
  
    
    /**
     * 
     * putResponse  构建返回参数 
     *
     * @param response
     * @param code
     * @param msg   
     * @since 1.0
     * @author op.nie-dongjia
     */
    private void putResponse(Map<String, Object> response, String code, String msg)
    {
      response.put("errorCode", code);
      response.put("errorMsg", msg);
    }
}
