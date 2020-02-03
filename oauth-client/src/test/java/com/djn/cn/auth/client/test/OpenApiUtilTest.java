package com.djn.cn.auth.client.test;




import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.alibaba.fastjson.JSONObject;
import com.djn.cn.auth.client.base.exception.BusinessException;
import com.djn.cn.auth.client.base.utils.HttpClientThreadUtil;
import com.djn.cn.auth.client.base.utils.OpenApiUtils;




public class OpenApiUtilTest extends AbstractTestCase{
	@Autowired
	private OpenApiUtils openApiUtils;
	@Test
	public void findByOpenIdTest() {
		String openId = "f95ba93aee210bc19fa5e9a791708a1b6269f60855e3ee4a67c308331b4d95e181b1a11c0b09d5eeb0826bb8204b0580f14255a7721133e5";
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", "core.user.get");
		params.put("key", "openId");
		params.put("value", openId);
		JSONObject userDetailRes = openApiUtils.getOpenApiResult(params, "USER");
		System.out.println(userDetailRes);
	}
	@Test
	public void findByUserIdTest() {
		String userId = "testTe1";
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", "core.user.get");
		params.put("key", "loginName");
		params.put("value", userId);
		JSONObject userDetailRes = openApiUtils.getOpenApiResult(params, "USER");
		System.out.println(userDetailRes);
	}
	@Test
	public void findByUserIdTest2() {
		String userId = "2000000056000000247";
		Map<String, String> params = new HashMap<String, String>();
		params.put("method", "core.user.get");
		params.put("key", "id");
		params.put("value", userId);
		JSONObject userDetailRes = openApiUtils.getOpenApiResult(params, "USER");
		
		System.out.println(userDetailRes);
	}
	@Test
	public void getOAuthToken() {
	    String userId = "2000000056000000247";
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("method", "core.user.get");
	    params.put("key", "id");
	    params.put("value", userId);
	    JSONObject userDetailRes = openApiUtils.getOpenApiResult(params, "USER");
	    System.out.println(userDetailRes);
	}
	
	
	
	
	
	
	@Test
	public void getTokenTest() {
	    String accessToken = null;
	    String openAuthorizationUrl = openApiUtils.getOpenAuthorizationUrl();
	    Map<String, String> params = openApiUtils.getOauthParamsMap();
        JSONObject object = null;
        try {
            String rusult1 = HttpClientThreadUtil.singlePost(openAuthorizationUrl, params);
            object = (JSONObject) JSONObject.parse(rusult1);
            System.out.println("obj:"+object);
            accessToken = object.getString("access_token");
            logger.error("获取accessToken："+accessToken);
        } catch (Exception e) {
            throw new BusinessException("调用open.authorization.url接口   参数=" + params + "失败" + "\n"
                    + e.getMessage());
        }
	    
	}
	
	@Test
	public void getUserInfoTest() {
	    String id = "f95ba93aee210bc19fa5e9a791708a1b6269f60855e3ee4a67c308331b4d95e181b1a11c0b09d5eeb0826bb8204b0580f14255a7721133e5";
        Map<String, String> params = new HashMap<String, String>();
        params.put("method", "core.user.get");
        params.put("key", "openId");
        params.put("value", id);
        JSONObject userDetailRes = openApiUtils.getOpenApiResult(params, "USER");
        System.out.println(userDetailRes);
	    
	}
	
	
	
	

}