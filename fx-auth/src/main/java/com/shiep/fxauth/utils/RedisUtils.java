package com.shiep.fxauth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author: 倪明辉
 * @date: 2019/3/26 15:31
 * @description: 封装RedisTemplate，redis的操作工具类
 * String: 字符串 key为String类型
 * Hash: 散列 key，value都是String类型
 * List: 列表
 * Set: 集合
 * Sorted Set: 有序集合
 */
@Component
public class RedisUtils<k,v> {

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    private static RedisUtils redisUtils;

    private RedisTemplate<Object,Object> getInstance(){
        return redisTemplate;
    }

    @PostConstruct
    public void init() {
        redisUtils = this;
        // 初使化时将已静态化的redisUtils实例化
        redisUtils.redisTemplate = this.redisTemplate;
    }

    /**
     * description: 操作类型：String类型。设置指定 key 的值
     *
     * @param key 键
     * @param value 值
     * @return void
     */
    public static void set(String key,Object value){
        redisUtils.getInstance().opsForValue().set(key, value);
    }


    /**
     * description: 操作类型：String类型。获取指定 key 的值。
     *
     * @param key 键
     * @return java.lang.String
     */
    public static Object get(String key){
        return redisUtils.getInstance().opsForValue().get(key);
    }

    /**
     * description: 操作类型：String类型。设置指定key的值和过期时间。
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间（单位：毫秒ms）
     * @return void
     */
    public static void setForTimeMS(String key,Object value,long time){
        redisUtils.getInstance().opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
    }

    /**
     * description: 操作类型：String类型。设置指定key的值和过期时间。
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间（单位：分钟min）
     * @return void
     */
    public static void setForTimeMIN(String key,Object value,long time){
        redisUtils.getInstance().opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }


    /**
     * description: 操作类型：String类型。设置指定key的值,过期时间和时间单位。
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @param type 过期时间单位
     * @return void
     */
    public static void setForTimeCustom(String key,Object value,long time,TimeUnit type){
        redisUtils.getInstance().opsForValue().set(key, value, time, type);
    }

    /**
     * description: 操作类型：String类型。如果key存在则覆盖，并返回旧值。如果不存在，返回null并添加。
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Object
     */
    public static Object getAndSet(String key,Object value){
        return redisUtils.getInstance().opsForValue().getAndSet(key, value);
    }

    /**
     * description: 操作类型：String类型。批量添加 key-value (重复的键会覆盖)
     *
     * @param keyAndValue map类型
     * @return void
     */
    public static void batchSet(Map<String,Object> keyAndValue){
        redisUtils.getInstance().opsForValue().multiSet(keyAndValue);
    }

    /**
     * description: 操作类型：String类型。批量添加key-value只有在键不存在时,才添加。map中只要有一个key存在,则全部不添加。
     *
     * @param keyAndValue map类型
     * @return void
     */
    public static void batchSetIfAbsent(Map<String,Object> keyAndValue){
        redisUtils.getInstance().opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * description: 给一个指定key的值附加过期时间
     *
     * @param key 键
     * @param time 过期时间
     * @param type 过期时间单位
     * @return boolean
     */
    public static boolean expire(String key,long time,TimeUnit type){
        return redisUtils.getInstance().boundValueOps(key).expire(time, type);
    }

    /**
     * description: 移除指定key的过期时间
     *
     * @param key 键
     * @return boolean
     */
    public static boolean persist(String key){
        return redisUtils.getInstance().boundValueOps(key).persist();
    }

    /**
     * description: 获取指定key的过期时间
     *
     * @param key 键
     * @return java.lang.Long
     */
    public static Long getExpire(String key){
        return redisUtils.getInstance().boundValueOps(key).getExpire();
    }

    /**
     * description: 修改key的名字
     *
     * @param key 旧键
     * @param newKey 新键
     * @return void
     */
    public static void rename(String key,String newKey){
        redisUtils.getInstance().boundValueOps(key).rename(newKey);
    }

    /**
     * description: 删除key-value
     *
     * @param key 键
     * @return void
     */
    public static void delete(String key){
        redisUtils.getInstance().delete(key);
    }

    //========================hash操作===========================

    /**
     * description: 添加Hash键值对
     *
     * @param key 键
     * @param field 域
     * @param value 值
     * @return void
     */
    public static void put(String key, String field, String value){
        redisUtils.getInstance().opsForHash().put(key, field, value);
    }

    /**
     * description: 批量添加hash的键值对
     *
     * @param key 键
     * @param map field-value
     * @return void
     */
    public static void putAll(String key,Map<String,String> map){
        redisUtils.getInstance().opsForHash().putAll(key, map);
    }

    /**
     * description: 添加hash键值对，不存在的时候才添加
     *
     * @param key 键
     * @param field 域
     * @param value 值
     * @return boolean 结果信息
     */
    public static boolean putIfAbsent(String key, String field, String value){
        return redisUtils.getInstance().opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * description: 删除指定key和field的hash
     *
     * @param key 键
     * @param field 域
     * @return java.lang.Long 删除成功的数量
     */
    public static Long delete(String key, Object ...field){
        return redisUtils.getInstance().opsForHash().delete(key, field);
    }

    /**
     * description: 获取指定key下的field的值
     *
     * @param key 键
     * @param field 域
     * @return java.lang.Object 值value
     */
    public static Object getHashValue(String key,Object field){
        return redisUtils.getInstance().opsForHash().get(key, field);
    }

    /**
     * description: 获取key下的所有field和value
     *
     * @param key 键
     * @return java.util.Map<java.lang.Object,java.lang.Object>
     */
    public static Map<Object,Object> getHashEntries(String key){
        return redisUtils.getInstance().opsForHash().entries(key);
    }

    /**
     * description: 验证指定key下有没有指定的field
     *
     * @param key 键
     * @param field 域
     * @return boolean true：存在 false：不存在
     */
    public static boolean hasField(String key,Object field){
        return redisUtils.getInstance().opsForHash().hasKey(key, field);
    }

    /**
     * description: 获取key下的所有field字段名
     *
     * @param key 键
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> hashKeys(String key){
        return redisUtils.getInstance().opsForHash().keys(key);
    }

    /**
     * description: 获取指定hash下面的键值对数量
     *
     * @param key
     * @return java.lang.Long
     */
    public static Long hashSize(String key){
        return redisUtils.getInstance().opsForHash().size(key);
    }
}