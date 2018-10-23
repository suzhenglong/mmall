package com.mmall.util;

import com.mmall.common.RedisShardedPool;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.ShardedJedis;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.20 15:45
 */
@Slf4j
public class RedisShardedPoolUtil {

    /**
     * 设置key的有效期,单位为妙
     */
    public static Long expire(String key, int exTime) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getRedis();
            result = jedis.expire(key, exTime);
        } catch (Exception e) {
            log.error("expire key:{}, error:{}", e);
            jedis.close();
            return result;
        }
        jedis.close();
        return result;
    }

    /**
     * exTime 单位为妙
     */
    public static String setEx(String key, String value, int exTime) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getRedis();
            result = jedis.setex(key, exTime, value);
        } catch (Exception e) {
            log.error("setex key:{},value:{}, error:{}", key, value, e);
            jedis.close();
            return result;
        }
        jedis.close();
        return result;
    }


    public static String set(String key, String value) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getRedis();
            result = jedis.set(key, value);
        } catch (Exception e) {
            log.error("set key:{},value:{}, error:{}", key, value, e);
            jedis.close();
            return result;
        }
        jedis.close();
        return result;
    }

    public static String get(String key) {
        ShardedJedis jedis = null;
        String result = null;
        try {
            jedis = RedisShardedPool.getRedis();
            result = jedis.get(key);
        } catch (Exception e) {
            log.error("get key:{}, error:{}", e);
            jedis.close();
            return result;
        }
        jedis.close();
        return result;
    }

    public static Long del(String key) {
        ShardedJedis jedis = null;
        Long result = null;
        try {
            jedis = RedisShardedPool.getRedis();
            result = jedis.del(key);
        } catch (Exception e) {
            log.error("del key:{}, error:{}", e);
            jedis.close();
            return result;
        }
        jedis.close();
        return result;
    }

    public static void main(String[] args) {

        String set = RedisShardedPoolUtil.set("gender", "男");

        System.out.println("set-->" + set);

        String gender = RedisShardedPoolUtil.get("gender");
        System.out.println("gender-->" + gender);

        String birth = RedisShardedPoolUtil.setEx("birth", "2018-04-20", 60 * 10);
        System.out.println("birth-->" + birth);

        Long expire = RedisShardedPoolUtil.expire("gender", 60 * 4);
        System.out.println("expire-->" + expire);

        Long m = RedisShardedPoolUtil.del("m");
        System.out.println("m-->" + m);

    }

}
