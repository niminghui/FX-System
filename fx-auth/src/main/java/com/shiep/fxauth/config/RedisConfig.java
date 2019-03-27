package com.shiep.fxauth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: 倪明辉
 * @date: 2019/3/25 15:32
 * @description: 配置redis
 */
@Configuration
public class RedisConfig {

    /**
     * description: 配置Redis连接池
     *
     * @return redis.clients.jedis.JedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig poolConfig=new JedisPoolConfig();
        //最大空闲数
        poolConfig.setMaxIdle(30);
        //最大连接数
        poolConfig.setMaxTotal(50);
        //最大等待毫秒数
        poolConfig.setMaxWaitMillis(2000);
        return poolConfig;
    }

    /**
     * description: 配置Redis的连接工厂
     *
     * @param poolConfig Redis连接池
     * @return org.springframework.data.redis.connection.RedisConnectionFactory
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig poolConfig){
        JedisConnectionFactory connectionFactory=new JedisConnectionFactory(poolConfig);
        //获取单机的Redis配置
        RedisStandaloneConfiguration standaloneConfiguration=connectionFactory.getStandaloneConfiguration();
        connectionFactory.setHostName("127.0.0.1");
        connectionFactory.setPort(6379);
        //connectionFactory.setPassword("");
        return connectionFactory;
    }

    /**
     * description: 配置RedisTemplate
     *
     * @param redisConnectionFactory redis的连接工厂
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.Object,java.lang.Object>
     */
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<Object,Object> redisTemplate=new RedisTemplate<>();
        //RedisTemplate会自动初始化StringRedisSerializer，所以这里直接获取
        RedisSerializer stringRedisSerializer=redisTemplate.getStringSerializer();
        //设置字符串序列化器，这样Spring就会把Redis的Key当作字符串处理
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        //设置redisTemplate的连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        System.out.println(redisTemplate);
        return redisTemplate;
    }
}

