package com.djn.cn.auth.token.test;





import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;

import com.djn.cn.auth.token.base.util.RedisCache;

public class RedisTest extends AbstractTestCase{
    @Resource
    private RedisCache redisCache;
	@Test
	public void setTest() {
	    String key = "key";
	    String value = UUID.randomUUID().toString();
	    System.out.println( redisCache.set(key, value));
	}
	@Test
	public void getTest() {
	    String key = "key";
	    System.out.println( redisCache.get(key, String.class));
	}
}