package com.djn.cn.auth.token.base.util;

import java.util.UUID;

/**
 * 
 * @ClassName UUIDUtil
 * @Description  UUID工具类 
 * @author 聂冬佳
 * @date 2017年3月9日 下午3:22:42
 *
 */
public class UUIDUtil {
	/**
	 * 生成UUID 
	 * @Title  create  
	 * @return String   
	 *
	 */
	public static String create(){
		return UUID.randomUUID().toString();
	}
	/**
	 * 生成UUID 
	 * @Title  createId  
	 * @return String   
	 *
	 */
	public static String createId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
