package com.djn.cn.auth.token.base.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Repository("redisCache")
public class RedisCaheImpl implements RedisCache {
    private static final Logger log = LoggerFactory.getLogger(RedisCaheImpl.class);
    @Resource
    private JedisPool jedisPool;

    public String set(String key, Object value) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            String objectJson = JSON.toJSONString(value);
            return jedis.set(key, objectJson);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.exists(key).booleanValue();
        } catch (Exception e) {
            log.error("exception:", e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            String value = jedis.get(key);

            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public <T> List<T> getList(String key, Class<T> clazz) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            String value = jedis.get(key);

            return JSON.parseArray(value, clazz);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long expire(String key, int timeout) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.expire(key, timeout).longValue();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String hmSet(String key, Map<String, String> hash) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.hmset(key, hash);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Map<String, String> hGetAll(String key) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.hgetAll(key);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long del(String key) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.del(key).longValue();
        } finally {
            jedis.close();
        }
    }

    public boolean hSet(String key, String field, String value) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.hset(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("exception:", e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean hSet(byte[] key, byte[] field, byte[] value) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            jedis.hset(key, field, value);
            return true;
        } catch (Exception e) {
            log.error("exception:", e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public String hGet(String key, String field) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            log.error("exception:", e);
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean hExists(String key, String field) {
        Jedis jedis = this.jedisPool.getResource();
        try {
            return jedis.hexists(key, field).booleanValue();
        } catch (Exception e) {
            log.error("exception:", e);
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public long hDel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.hdel(key, new String[] { field }).longValue();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public byte[] hGet(byte[] key, byte[] field) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            return jedis.hget(key, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public byte[] hdel(byte[] key, byte[] field) {
        Jedis jedis = null;
        byte[] value = null;
        try {
            jedis = this.jedisPool.getResource();
            value = jedis.hget(key, field);
            jedis.hdel(key, new byte[][] { field });
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return value;
    }

    public void hdel(byte[] key) {
        Jedis jedis = null;
        try {
            jedis = this.jedisPool.getResource();
            jedis.hdel(key, new byte[0][]);
        } catch (Exception localException) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public int hlen(byte[] key) {
        Jedis jedis = null;
        int size = 0;
        try {
            jedis = this.jedisPool.getResource();
            size = jedis.hlen(key).intValue();
        } catch (Exception localException) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return size;
    }

    public Set<byte[]> hkeys(byte[] key) {
        Jedis jedis = null;
        Set<byte[]> keys = new HashSet<>();
        try {
            jedis = this.jedisPool.getResource();
            keys = jedis.hkeys(key);
        } catch (Exception localException) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return keys;
    }

    public Collection<byte[]> hgetAll(byte[] key) {
        Jedis jedis = null;
        Collection<byte[]> vals = Collections.emptyList();
        try {
            jedis = this.jedisPool.getResource();
            Map<byte[], byte[]> map = jedis.hgetAll(key);
            for (Map.Entry<byte[], byte[]> entry : map.entrySet()) {
                vals.add(entry.getValue());
            }
        } catch (Exception localException) {
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return vals;
    }
}
