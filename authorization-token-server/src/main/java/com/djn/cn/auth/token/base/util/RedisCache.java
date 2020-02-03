package com.djn.cn.auth.token.base.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 
 * <b>类   名：</b>RedisCache<br/>
 * <b>类描述：</b>TODO<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年1月17日 下午9:59:21<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年1月17日 下午9:59:21<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public interface RedisCache {
    String set(String paramString, Object paramObject);

    boolean exists(String paramString);

    <T> T get(String paramString, Class<T> paramClass);

    <T> List<T> getList(String paramString, Class<T> paramClass);

    long del(String paramString);

    boolean hSet(String paramString1, String paramString2, String paramString3);

    boolean hSet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3);

    String hGet(String paramString1, String paramString2);

    byte[] hGet(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

    boolean hExists(String paramString1, String paramString2);

    long hDel(String paramString1, String paramString2);

    long expire(String paramString, int paramInt);

    String hmSet(String paramString, Map<String, String> paramMap);

    Map<String, String> hGetAll(String paramString);

    byte[] hdel(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

    void hdel(byte[] paramArrayOfByte);

    int hlen(byte[] paramArrayOfByte);

    Set<byte[]> hkeys(byte[] paramArrayOfByte);

    Collection<byte[]> hgetAll(byte[] paramArrayOfByte);
}
