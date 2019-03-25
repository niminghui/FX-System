package com.shiep.fxauth.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 15:34
 * @description: 测试Redis
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis(){
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForHash().put("hash","field","hashValue");
        assertEquals("value1",redisTemplate.opsForValue().get("key1"));
        assertEquals("hashValue",redisTemplate.opsForHash().get("hash","field"));
    }
}
