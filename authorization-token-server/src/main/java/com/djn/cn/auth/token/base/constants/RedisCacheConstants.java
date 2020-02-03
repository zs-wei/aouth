package com.djn.cn.auth.token.base.constants;

/**
 * 
 * @ClassName RedisCacheConstants
 * @Description  RedisCache常量类 
 * @author 聂冬佳
 * @date 2017年12月15日 下午1:40:49
 *
 */
public class RedisCacheConstants {
	/**缓存10分钟*/
	public static final Long CACHE_XS_EXPIRE  = 600L;
	/**缓存1个小时*/
	public static final Long CACHE_S_EXPIRE   = 3600L;
	/**缓存6个小时*/
	public static final Long CACHE_M_EXPIRE   = 21600L;
	/**缓存12个小时*/
	public static final Long CACHE_L_EXPIRE   = 43200L;
	/**缓存24个小时*/
	public static final Long CACHE_XL_EXPIRE    = 86400L;
	/**永久缓存*/
	public static final Long CACHE_EVER_EXPIRE    = -1L;
	/**KEY模块名*/
	public static final String CACHE_KEY_MUDLE_NAME  = "AUTH-TOKEN";
}
