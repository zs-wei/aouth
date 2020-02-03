package com.djn.cn.auth.client.base.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.djn.cn.auth.client.base.constants.CommonConfig;
import com.djn.cn.auth.client.base.exception.BusinessException;

/**
 * 
 * @ClassName OpenApiUtils
 * @Description  OpenApiUtils
 * @author 聂冬佳
 * @date 2017年11月23日 上午10:34:24
 *
 */
public class OpenApiUtils {
	 protected Logger logger = LoggerFactory.getLogger(getClass());
	    /**
	     * 存放上一步获取的access_token
	     */
	    public static String accessToken = null;
	    
	    /**
	     * open-api接口地址
	     */
	    private String openApiUrl;

	    /**
	     * 开放平台授权服务地址
	     */
	    private String openAuthorizationUrl;

	    /**
	     * 应用Id
	     */
	    private String clientId;
	    /**
	     * 应用密钥
	     */
	    private String clientSecret;
	    /**
	     * 授权类型
	     */
	    private String openAuthorizationGrantType;

	    /**
	     * 返回值格式(json)
	     */
	    public final static String FORMAT = "json";

	    /**
	     * accessToke失效错误码
	     */
	    public final static int ACCESSTOKE_INVALID_CODE = 106;

	    private static OpenApiUtils openApiUtils = null;

	    /**
	     * getInstance(单例)  可以修改为懒加载 单例模式 
	     *
	     * @param
	     * @return com.djn.cn.auth.client.base.utils.OpenApiUtils
	     * @throws
	     * @version 2.0
	     * @author 聂冬佳
	     * @date 2017年11月23日 上午10:34:24
	     */
	    public static OpenApiUtils getInstance() {
	        if (openApiUtils == null) {
	            openApiUtils =
	                    ContextLoader.getCurrentWebApplicationContext().getBean(OpenApiUtils.class);
	        }
	        return openApiUtils;
	    }

	    /**
	     * 
	     * getOpenApiResult(结果处理)
	     * 
	     * @param params
	     * @param type 服务类型(不能为空)
	     * @return
	     * @exception
	     * @since 4.0
	     * @author 聂冬佳
	     */
	    public JSONObject getOpenApiResult(Map<String, String> params, String type) {
	        JSONObject jsonRet = null;
	        String rusult = null;
	        String openApiUrl = getOpenApiUrl(type);
	        if (accessToken == null) {
	            setAccessToken();
	        }
	        params.put("access_token", accessToken);
	        params.putAll(getCommParamsMap(type));
	        try {

	            rusult = HttpClientThreadUtil.singlePost(openApiUrl, params);
	            logger.debug(rusult);
	            logger.debug("调用open.api.url接口:" + openApiUrl + "\t参数=" + params);
	            // 如果不access_token失效或不可用，则调用开放平台授权服务地址
	            JSONObject object = (JSONObject) JSONObject.parse(rusult);
	            if (object.getInteger("code") == ACCESSTOKE_INVALID_CODE) {
	                setAccessToken();
	                params.put("access_token", accessToken);
	                rusult = HttpClientThreadUtil.singlePost(openApiUrl, params);
	            }

	            jsonRet = JSONObject.parseObject(rusult);
	            String fullUrl = openApiUrl;
	            Iterator iterator = params.entrySet().iterator();
	            while (iterator.hasNext()) {
	                Map.Entry entry = (Map.Entry) iterator.next();
	                if (fullUrl.contains("?")) {
	                    fullUrl += "&" + entry.getKey() + "=" + entry.getValue();
	                } else {
	                    fullUrl += "?" + entry.getKey() + "=" + entry.getValue();
	                }
	            }
	            logger.error(fullUrl);
	        } catch (JSONException e) {
	            return jsonRet;
	        } catch (Exception e) {
	            logger.error("调用open.api.url接口  参数=" + params + "失败" + "\n" + e.getMessage());
	            throw new BusinessException("调用open.api.url接口  参数=" + params + "失败" + "\n"
	                    + e.getMessage());
	        }
	        logger.debug("调用open.api.url接口: " + openApiUrl + "\tmethod:" + params.get("method")
	                + "\t结果=" + JSON.toJSONString(jsonRet));
	        return jsonRet;
	    }

	    /**
	     *
	     * getOpenApiResultGET(结果处理,GET方式)
	     *
	     * @param params
	     * @param type 服务类型(不能为空)
	     * @return
	     * @exception
	     * @since 4.0
	     * @author 聂冬佳
	     */
	    public JSONObject getOpenApiResultGET(Map<String, String> params, String type) {
	        JSONObject jsonRet = null;
	        String rusult = null;
	        String openApiUrl = getOpenApiUrl(type);
	        if (accessToken == null) {
	            setAccessToken();
	        }
	        params.put("access_token", accessToken);
	        params.putAll(getCommParamsMap(type));
	        try {
	            rusult = HttpClientThreadUtil.singlePost(openApiUrl, params);
	            logger.debug(rusult);
	            logger.debug("调用open.api.url接口:" + openApiUrl + "\t参数=" + params);
	            // 如果不access_token失效或不可用，则调用开放平台授权服务地址
	            JSONObject object = (JSONObject) JSONObject.parse(rusult);
	            if (object.getInteger("code") == ACCESSTOKE_INVALID_CODE) {
	                setAccessToken();
	                params.put("access_token", accessToken);
	                rusult = HttpClientThreadUtil.singlePost(openApiUrl, params);
	            }

	            jsonRet = JSONObject.parseObject(rusult);
	        } catch (Exception e) {
	            logger.error("url:" + openApiUrl);
	            logger.error("调用open.api.url接口  参数=" + params + "失败" + "\n" + e.getMessage());
	            throw new BusinessException("调用open.api.url接口  参数=" + params + "失败" + "\n"
	                    + e.getMessage());
	        }
	        logger.debug("调用open.api.url接口: " + openApiUrl + "\tmethod:" + params.get("method")
	                + "\t结果=" + JSON.toJSONString(jsonRet));
	        return jsonRet;
	    }

	    public String getOpenApiUrl(String type) {
	        String openApiUrl = getOpenApiUrl();
	        
	        return openApiUrl;
	    }

	    public void setAccessToken() {
	        String openAuthorizationUrl = getOpenAuthorizationUrl();
	        Map<String, String> params = getOauthParamsMap();
	        JSONObject object = null;
	        try {
	            String rusult1 = HttpClientThreadUtil.singlePost(openAuthorizationUrl, params);
	            object = (JSONObject) JSONObject.parse(rusult1);
	            accessToken = object.getString("access_token");
	            logger.error("获取accessToken："+accessToken);
	        } catch (Exception e) {
	            throw new BusinessException("调用open.authorization.url接口   参数=" + params + "失败" + "\n"
	                    + e.getMessage());
	        }
	    }

	    /**
	     * 
	     * getOauthParamsMap(获取授权接口参数)
	     * 
	     * @return
	     * @exception @since 4.0
	     * @author 聂冬佳
	     */
	    public Map<String, String> getOauthParamsMap() {
	        Map<String, String> retMap = new HashMap<String, String>();
	        String clientId = getClientId();
	        String clientSecret = getClientSecret();
	        String grantType = getOpenAuthorizationGrantType();
	        retMap.put("client_id", clientId);
	        retMap.put("client_secret", clientSecret);
	        retMap.put("grant_type", grantType);
	        return retMap;
	    }

	    /**
	     * 
	     * getCommParamsMap(获取公共参数信息)
	     * 
	     * @return
	     * @exception @since 4.0
	     * @author 聂冬佳
	     */
	    public Map<String, String> getCommParamsMap(String type) {
	        Map<String, String> retMap = new HashMap<String, String>();
	        String clientId = getClientId();
	        String version = "";
	        if (StringUtils.isNotBlank(type) && type.equals(CommonConfig.PAN_SERVER_TYPE)) {
	            version = "2.0";
	        } else {
	            version = "1.0";
	        }
	        retMap.put("appkey", clientId);
	        retMap.put("version", version);
	        retMap.put("format", FORMAT);
	        return retMap;
	    }


	    public String getOpenAuthorizationUrl() {
	        return openAuthorizationUrl;
	    }

	    public void setOpenAuthorizationUrl(String openAuthorizationUrl) {
	        this.openAuthorizationUrl = openAuthorizationUrl;
	    }

	    public String getClientId() {
	        return clientId;
	    }

	    public void setClientId(String clientId) {
	        this.clientId = clientId;
	    }

	    public String getClientSecret() {
	        return clientSecret;
	    }

	    public void setClientSecret(String clientSecret) {
	        this.clientSecret = clientSecret;
	    }

	    public String getOpenAuthorizationGrantType() {
	        return openAuthorizationGrantType;
	    }

	    public void setOpenAuthorizationGrantType(String openAuthorizationGrantType) {
	        this.openAuthorizationGrantType = openAuthorizationGrantType;
	    }

	    public String getOpenApiUrl() {
	        return openApiUrl;
	    }

	    public void setOpenApiUrl(String openApiUrl) {
	        this.openApiUrl = openApiUrl;
	    }
	
}
