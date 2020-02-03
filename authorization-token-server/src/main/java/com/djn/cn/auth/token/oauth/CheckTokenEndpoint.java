package com.djn.cn.auth.token.oauth;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.djn.cn.auth.token.base.util.LogUtil;
import com.djn.cn.auth.token.business.CheckTokenBusiness;
/**
 * 
 * <b>类   名：</b>CheckTokenEndpoint<br/>
 * <b>类描述：</b>CheckTokenEndpoint<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年3月23日 下午6:50:03<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年3月23日 下午6:50:03<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
@FrameworkEndpoint
public class CheckTokenEndpoint {
    private static final Logger  log = LoggerFactory.getLogger(CheckTokenEndpoint.class);
    @Autowired
    private CheckTokenBusiness checkTokenBusiness;
    public CheckTokenEndpoint()
    {
    }
    @RequestMapping({"/oauth/check_token"})
    @ResponseBody
    public Map<String, Object> checkToken(String token, String appkey, String serviceName, String interfaceName, String interfaceUrl, String clientIp, HttpServletRequest request)
    {
      if (!StringUtils.isNoneEmpty(new CharSequence[] { token })) {
        if (!StringUtils.isNoneEmpty(new CharSequence[] { appkey })) {}
      }
      long startTime = System.currentTimeMillis();
      Map<String, Object> response = new HashMap<>();
      checkTokenBusiness.doCheck(token, appkey, serviceName, interfaceName, interfaceUrl, clientIp, response);  
      LogUtil.writeAccessLog(log, request, startTime, new String[] { "token:" + token, "appkey:" + appkey, "serviceName:" + serviceName, "interfaceName:" + interfaceName, "interfaceUrl" + interfaceUrl });
    
      return response;
    }
    @RequestMapping({"/oauth/check_token_only"})
    @ResponseBody
    public Map<String, Object> checkTokenOnly(String token, String appkey, String clientIp, HttpServletRequest request)
    {
      long startTime = System.currentTimeMillis();
      Map<String, Object> response = new HashMap<>();
      
      this.checkTokenBusiness.doCheckTokenOnly(token, appkey, clientIp, response);
      
      LogUtil.writeAccessLog(log, request, startTime, new String[] { "token:" + token, "appkey:" + appkey });
      

      return response;
    }
}
