package com.dnslog.web.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> template;
    private RedisConnectionFactory connectionFactory;
    /**
     * 读取数据
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return template.opsForValue().get(key);
    }

    /**
     * 写入数据
     */
    public boolean set(final String key, String value) {
        boolean res = false;
        try {
            template.opsForValue().set(key, value);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据key更新数据
     */
    public boolean update(final String key, String value) {
        boolean res = false;
        try {
            template.opsForValue().getAndSet(key, value);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 根据key删除数据
     */
    public boolean del(final String key) {
        boolean res = false;
        try {
            template.delete(key);
            res = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 是否存在key
     */
    public boolean hasKey(final String key) {
        boolean res = false;
        try {
            res = template.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 给指定的key设置存活时间
     * 默认为-1，表示永久不失效
     */
    public boolean setExpire(final String key, long seconds) {
        boolean res = false;
        try {
            if (0 < seconds) {
                res = template.expire(key, seconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 获取指定key的剩余存活时间
     * 默认为-1，表示永久不失效，-2表示该key不存在
     */
    public long getExpire(final String key) {
        long res = 0;
        try {
            res = template.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 移除指定key的有效时间
     * 当key的有效时间为-1即永久不失效和当key不存在时返回false，否则返回true
     */
    public boolean persist(final String key) {
        boolean res = false;
        try {
            res = template.persist(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public void setIncr(String key, int value) {
        if (value < 0) {
            value = 1;
        }
        if (null == connectionFactory) {
            connectionFactory = template.getConnectionFactory();
        }
        assert connectionFactory != null;
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, connectionFactory);
        entityIdCounter.set(value);
    }
    public Long incr(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, template.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();
        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.SECONDS);
        }

        return increment;
    }
}
