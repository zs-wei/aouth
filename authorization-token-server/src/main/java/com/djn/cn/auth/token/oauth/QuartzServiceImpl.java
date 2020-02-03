package com.djn.cn.auth.token.oauth;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.djn.cn.auth.token.base.util.RedisCache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
/**
 * 
 * <b>类   名：</b>QuartzServiceImpl<br/>
 * <b>类描述：</b>定时任务<br/>
 * <b>创建人：</b>op.nie-dongjia<br/>
 * <b>创建时间：</b>2019年3月24日 上午9:40:49<br/>
 * <b>修改人：</b>op.nie-dongjia<br/>
 * <b>修改时间：</b>2019年3月24日 上午9:40:49<br/>
 * <b>修改备注：</b><br/>
 *
 * @version   1.0<br/>
 *
 */
public class QuartzServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(QuartzServiceImpl.class);
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private JedisPool jedisPool;

    public void clearDailyFlow() {
        List<String> dailyFlowKeys = this.redisCache.getList("dailyFlowKeys", String.class);
        if ((dailyFlowKeys == null) || (dailyFlowKeys.size() == 0)) {
            log.info("暂无接口调用信息！");
        } else {
            Jedis jedis = this.jedisPool.getResource();
            int i = 0;
            for (int len = dailyFlowKeys.size(); i < len; i++) {
                String key = (String) dailyFlowKeys.get(i);
                jedis.set(key, "0");
                log.info("成功清零接口当日调用量！");
            }
            jedis.close();
        }
    }
}
