package com.gtmp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@Component
public class RedisOpsUtil {
    private RedisOpsUtil(){}


    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisOpsUtil.redisTemplate = redisTemplate;
    }


    public static Object hget(String key,Object field){
        return redisTemplate.opsForHash().get(key,field);
    }
}
