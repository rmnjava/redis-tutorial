package com.charlie.weibo.conf;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by dhy on 17-3-22.
 * redis配置文件
 */
@Configuration
public class RedisConf {

    @Autowired
    public RedisConf(@Value("${spring.redis.host}") String host,
                     @Value("${spring.redis.port}") int port,
                     @Value("${spring.redis.database}") int database,
                     @Value("${spring.redis.password}") String password,
                     @Value("${spring.redis.timeout}") int timeout,
                     @Value("${spring.redis.blockWhenExhausted}") boolean blockWhenExhausted,
                     @Value("${spring.redis.maxIdle}") int maxIdle,
                     @Value("${spring.redis.maxTotal}") int maxTotal,
                     @Value("${spring.redis.minEvictableIdleTimeMillis}") int minEvictableIdleTimeMillis) {

        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
        this.database = database;

        this.blockWhenExhausted = blockWhenExhausted;
        this.maxIdle = maxIdle;
        this.maxTotal = maxTotal;
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }


    @Bean
    public JedisPool redisPool() {
        logger.info("the redis pool start initialize...");
        // 我本地的redis是没有密码的,如果传密码的话会抛出异常
        return new JedisPool(jedisPoolConfig(), host, port, timeout, null, database);
//        return new JedisPool(jedisPoolConfig(), host, port, timeout, password, database);
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        // 这里偷了下懒,有一个属性没有输出日志
        logger.info(String.format("the redis pool config is initializing : block=[%s], maxIdle=[%d], maxTotal=[%d]", blockWhenExhausted, maxIdle, maxTotal));
        JedisPoolConfig config = new JedisPoolConfig();
        config.setBlockWhenExhausted(blockWhenExhausted);
        config.setMaxIdle(maxIdle);
        config.setMaxTotal(maxTotal);
        config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        return config;
    }

    private String host;
    private int port;
    private int database;
    private int timeout;
    private String password;

    private boolean blockWhenExhausted;
    private int maxIdle;
    private int maxTotal;
    private int minEvictableIdleTimeMillis;

    private final static Logger logger = Logger.getLogger(RedisConf.class);
}
