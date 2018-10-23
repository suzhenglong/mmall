package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import redis.clients.jedis.*;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: zhenglongsu@163.com
 * @date: 2018.04.24 16:25
 */
public class RedisShardedPool {

    /**
     * jedis连接池
     */
    private static ShardedJedisPool jedisPool;
    /**
     * 控制jedis连接池和Redis-Server之间最大的连接数
     */
    private static Integer maxTotal = Integer.parseInt(PropertiesUtil.getProperty("reids.max.total", "20"));
    /**
     * 在jedisPool中最大的idle状态(空闲)的jedis实例的个数
     */
    private static Integer minIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.max.idle", "10"));
    /**
     * 在jedisPool中最小的idle状态(空闲)的jedis实例的个数
     */
    private static Integer maxIdle = Integer.parseInt(PropertiesUtil.getProperty("redis.min.idle", "2"));
    ;
    /**
     * 在Borrow一个jedis实例时,是否要进行验证操作,赋值为true,则得到的jedis实例肯定为可用的
     */
    private static boolean testOnBorrow = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.borrow", "true"));
    /**
     * 在return一个jedis实例时,是否要进行验证操作,赋值为true,则放回jedisPool的jedis实例肯定为可用的
     */
    private static boolean testOnReturn = Boolean.parseBoolean(PropertiesUtil.getProperty("redis.test.return", "true"));

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip", null);
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port", null));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip", null);
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port", null));


    private static void initPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

        jedisPoolConfig.setMaxTotal(maxTotal);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMinIdle(minIdle);

        jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        jedisPoolConfig.setTestOnReturn(testOnReturn);
        //连接耗尽时,是否阻塞,false会抛出异常,true会阻塞直到超时,默认为true
        jedisPoolConfig.setBlockWhenExhausted(true);

        JedisShardInfo info1 = new JedisShardInfo(redis1Ip, redis1Port, 1000 * 2);
        JedisShardInfo info2 = new JedisShardInfo(redis2Ip, redis2Port, 1000 * 2);

        List<JedisShardInfo> jedisShardInfoList = new ArrayList<>();
        jedisShardInfoList.add(info1);
        jedisShardInfoList.add(info2);

        jedisPool = new ShardedJedisPool(jedisPoolConfig, jedisShardInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);

    }

    static {
        initPool();
    }

    public static ShardedJedis getRedis() {
        return jedisPool.getResource();
    }

    public static void main(String[] args) {
        ShardedJedis jedis = jedisPool.getResource();
        for (int i = 0; i < 10; i++) {
            jedis.set("key_" + i, "value_" + i);
        }
        for (int i = 0; i < 10; i++) {
            System.out.println(jedis.get("key_"+i));
        }
        jedis.close();
    }


}
