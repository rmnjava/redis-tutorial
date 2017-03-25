package com.charlie.weibo.service;

import com.charlie.weibo.entity.User;
import com.charlie.weibo.constant.KeyGenerator;
import com.charlie.weibo.utils.SerializeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import java.util.List;

/**
 * Created by dhy on 17-3-22.
 *
 */
@Service
public class UserService {
    @Autowired
    public UserService(JedisPool jedisPool, JedisSentinelPool sentinelPool, JedisCluster jedisCluster) {
        this.jedisPool = jedisPool;
        this.sentinelPool = sentinelPool;
        this.jedisCluster = jedisCluster;
    }

    public long register(User user) {
//        Jedis jedis = jedisPool.getResource();
        Jedis jedis = sentinelPool.getResource();
        boolean isMember = jedis.sismember(KeyGenerator.USER_NAME, user.getUserName());
        jedis.watch(KeyGenerator.USER_ID, KeyGenerator.USER_NAME);
        Transaction transaction = jedis.multi();
        // only execute the transaction when the username does register
        if (!isMember) {
            Response<Long> userId = transaction.incr(KeyGenerator.USER_ID);
            transaction.sadd(KeyGenerator.USER_NAME, user.getUserName());
            List<Object> affected = transaction.exec();
            if (affected.size() > 0 && userId.get() != null) {
                user.setId(userId.get());
                jedis.set(KeyGenerator.generateUserKey(userId.get()).getBytes(), SerializeUtils.serializeObject(user));
                jedis.close();
                return userId.get();
            }
        }

        jedis.close();
        return -1;
    }

    private JedisPool jedisPool;

    private JedisSentinelPool sentinelPool;

    private JedisCluster jedisCluster;
}
