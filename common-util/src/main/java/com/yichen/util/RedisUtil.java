package com.yichen.util;

import lombok.val;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author dengbojing
 */
public class RedisUtil {
    private StringRedisTemplate stringRedisTemplate;

    private RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
    }

    /**
     * 设置值
     *
     * @param key   key
     * @param value value
     */
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key(key), value);
    }

    /**
     * 设置值和过期时间
     *
     * @param key      key
     * @param value    value
     * @param timeOut  超时时间
     * @param timeUnit 时间单位
     */
    public void set(String key, String value, Integer timeOut, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key(key), value, timeOut, timeUnit);
    }

    /**
     * 获取值
     *
     * @param key key
     * @return value
     */
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key(key));
    }

    /**
     * 增量
     *
     * @param key   key
     * @param delta 增量
     * @return 当前值
     */
    public Long increment(String key, long delta) {
        return stringRedisTemplate.opsForValue().increment(key(key), delta);
    }

    /**
     * redis key 生成策略：加上不同服务的前缀
     *
     * @param key 原始key
     * @return 包装key
     */
    public String key(String key) {
        return key;
    }

    /**
     * key 是否不存在
     *
     * @param key key
     * @return true 为不存在
     */
    public Boolean no(String key) {
        val exist = stringRedisTemplate.hasKey(key(key));
        if (exist == null) {
            return true;
        }
        return !exist;
    }

    public void remove(String key) {
        stringRedisTemplate.delete(key(key));
    }

    /**
     * keys查询
     *
     * @param pattern 匹配模式
     * @return keys列表
     */
    public Set<String> keys(String pattern) {
        return stringRedisTemplate.keys(key(pattern));
    }

    /**
     * 获取过期时间
     *
     * @param key key
     * @return 秒
     */
    public Long getExpire(String key) {
        return stringRedisTemplate.getExpire(key(key), TimeUnit.SECONDS);
    }

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public Object setCacheList(String key, List<?> dataList) {
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        if (null != dataList) {
            int size = dataList.size();
            for (Object aDataList : dataList) {
                listOperation.rightPush(key, aDataList);
            }
        }
        return listOperation;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<?> getCacheList(String key) {
        List<Object> dataList = new ArrayList<Object>();
        ListOperations<String, Object> listOperation = redisTemplate.opsForList();
        Long size = listOperation.size(key);

        for (int i = 0; i < size; i++) {
            dataList.add(listOperation.leftPop(key));
        }
        return dataList;
    }

    /**
     * 加锁
     *
     * @param key 加锁的key
     * @return 是否加锁成功
     */
    public Boolean lock(String key) {
        return lock(key, 3);
    }

    /**
     * 加锁
     *
     * @param key     加锁的key
     * @param seconds 锁有效时间
     * @return 是否加锁成功
     */
    public Boolean lock(String key, Integer seconds) {
        return lock(key, seconds, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param key      加锁的key
     * @param time     锁有效时间
     * @param timeUnit 时间单位
     * @return 是否加锁成功
     */
    public Boolean lock(String key, Integer time, TimeUnit timeUnit) {
        return lock(key, "", time, TimeUnit.SECONDS);
    }

    /**
     * 加锁
     *
     * @param key      加锁的key
     * @param value    加锁的key对应的value
     * @param time     锁有效时间
     * @param timeUnit 时间单位
     * @return 是否加锁成功
     */
    public Boolean lock(String key, String value, Integer time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    /**
     * 释放锁
     *
     * @param key 加锁的key
     */
    public void unlock(String key) {
        stringRedisTemplate.delete(key);
    }
}
