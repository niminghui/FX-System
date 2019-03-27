package com.shiep.fxauth.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.core.ZSetOperations;
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
 * Hash: 散列 key，value都是String类型 ==》方法以“h”打头
 * List: 列表 ==》方法以“l”打头
 * Set: 集合 ==》方法以“s”打头
 * Sorted Set: 有序集合 ==》方法以“z”打头
 */
@Component
@SuppressWarnings("all")
public class RedisUtils {

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
    public static void hPut(String key, String field, Object value){
        redisUtils.getInstance().opsForHash().put(key, field, value);
    }

    /**
     * description: 批量添加hash的键值对
     *
     * @param key 键
     * @param map field-value
     * @return void
     */
    public static void hPutAll(String key,Map<String,Object> map){
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
    public static boolean hPutIfAbsent(String key, String field, Object value){
        return redisUtils.getInstance().opsForHash().putIfAbsent(key, field, value);
    }

    /**
     * description: 删除指定key和field的hash
     *
     * @param key 键
     * @param field 域
     * @return java.lang.Long 删除成功的数量
     */
    public static Long hDelete(String key, Object... field){
        return redisUtils.getInstance().opsForHash().delete(key, field);
    }

    /**
     * description: 获取指定key下的field的值
     *
     * @param key 键
     * @param field 域
     * @return java.lang.Object 值value
     */
    public static Object hGet(String key,Object field){
        return redisUtils.getInstance().opsForHash().get(key, field);
    }

    /**
     * description: 获取key下的所有field和value
     *
     * @param key 键
     * @return java.util.Map<java.lang.Object,java.lang.Object>
     */
    public static Map<Object,Object> hGetEntries(String key){
        return redisUtils.getInstance().opsForHash().entries(key);
    }

    /**
     * description: 验证指定key下有没有指定的field
     *
     * @param key 键
     * @param field 域
     * @return boolean true：存在 false：不存在
     */
    public static boolean hHasField(String key,Object field){
        return redisUtils.getInstance().opsForHash().hasKey(key, field);
    }

    /**
     * description: 获取key下的所有field字段名
     *
     * @param key 键
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> hGetFields(String key){
        return redisUtils.getInstance().opsForHash().keys(key);
    }

    /**
     * description: 获取指定hash下面的键值对数量
     *
     * @param key
     * @return java.lang.Long
     */
    public static Long hSize(String key){
        return redisUtils.getInstance().opsForHash().size(key);
    }

    //========================list操作===========================

    /**
     * description: 指定list从左入栈
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Long 当前队列的长度
     */
    public static Long lLeftPush(Object key,Object value){
        return redisUtils.getInstance().opsForList().leftPush(key, value);
    }

    /**
     * description: 指定list从左出栈。如果列表没有元素,会堵塞到列表一直有元素或者超时为止
     *
     * @param key 键
     * @return java.lang.Object 出栈的值
     */
    public static Object lLeftPop(Object key){
        return redisUtils.getInstance().opsForList().leftPop(key);
    }

    /**
     * description: 从左边依次入栈，导入顺序按照Collection顺序，如: a b c => c b a
     *
     * @param key 键
     * @param values 值
     * @return java.lang.Long 当前队列的长度
     */
    public static Long lLeftPushAll(Object key, Collection<Object> values){
        return redisUtils.getInstance().opsForList().leftPushAll(key, values);
    }

    /**
     * description: 指定list从右入栈
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Long 当前队列的长度
     */
    public static Long lRightPush(Object key,Object value){
        return redisUtils.getInstance().opsForList().rightPush(key, value);
    }

    /**
     * description: 指定list从右出栈。如果列表没有元素，会堵塞到列表一直有元素或者超时为止。
     *
     * @param key 键
     * @return java.lang.Object 返回的值
     */
    public static Object lRightPop(Object key){
        return redisUtils.getInstance().opsForList().rightPop(key);
    }

    /**
     * description: 从右边依次入栈，导入顺序按照Collection顺序，如: a b c => a b c
     *
     * @param key 键
     * @param values 值
     * @return java.lang.Long 当前队列的长度
     */
    public static Long lRightPushAll(Object key,Collection<Object> values){
        return redisUtils.getInstance().opsForList().rightPushAll(key, values);
    }

    /**
     * description: 根据下标获取值
     *
     * @param key 键
     * @param index 下标
     * @return java.lang.Object 返回的值
     */
    public static Object lIndex(Object key,long index){
        return redisUtils.getInstance().opsForList().index(key, index);
    }

    /**
     * description: 获取列表key的长度
     *
     * @param key 键
     * @return java.lang.Long list的长度
     */
    public static Long lSize(Object key){
        return redisUtils.getInstance().opsForList().size(key);
    }

    /**
     * description: 获取列表key指定范围内的所有值
     *
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标
     * @return java.util.List<java.lang.Object>
     */
    public static List<Object> lRange(Object key, long start, long end){
        return redisUtils.getInstance().opsForList().range(key, start, end);
    }

    /**
     * description: 删除key中值为value的元素count个。
     *
     * @param key 键
     * @param count 删除个数
     * @param value 匹配值
     * @return java.lang.Long
     */
    public static Long lRemove(Object key,long count,Object value){
        return redisUtils.getInstance().opsForList().remove(key, count, value);
    }

    /**
     * description: 删除key列表[start,end]以外的所有元素
     *
     * @param key 键
     * @param start 开始下标
     * @param end 结束下标
     * @return void
     */
    public static void lTrim(Object key,long start,long end){
        redisUtils.getInstance().opsForList().trim(key, start, end);
    }

    /**
     * description: 将key列表右出栈,并左入栈到newKey
     *
     * @param key 右出栈的列表
     * @param newKey 左入栈的列表
     * @return java.lang.Object 操作的值
     */
    public static Object lRightPopAndLeftPush(Object key,Object newKey){
        return redisUtils.getInstance().opsForList().rightPopAndLeftPush(key, newKey);

    }

    //========================Set（无序不重复集合）操作===========================

    /**
     * description: 给集合key添加多个值，集合不存在将创建后再添加
     *
     * @param key 键
     * @param values 值
     * @return java.lang.Long 集合大小
     */
    public static Long sAdd(Object key ,Object... values){
        return redisUtils.getInstance().opsForSet().add(key, values);
    }

    /**
     * description: 获取两个集合的差集
     *
     * @param key 集合键
     * @param otherkey 另一个集合的键
     * @return java.util.Set<java.lang.Object> 差集
     */
    public static Set<Object> sDifference(Object key ,Object otherkey){
        return redisUtils.getInstance().opsForSet().difference(key, otherkey);
    }

    /**
     * description: 获取key集合和collections中的otherKeys所有集合的差集
     *
     * @param key 集合键
     * @param otherKeys 其他集合键
     * @return java.util.Set<java.lang.Object> 差集
     */
    public static Set<Object> sDifference(Object key ,Collection<Object> otherKeys){
        return redisUtils.getInstance().opsForSet().difference(key, otherKeys);
    }

    /**
     * description: 将key集合与otherkey集合的差集 ,添加到新的newKey集合中
     *
     * @param key 集合键
     * @param otherkey 另个集合的键
     * @param newKey 差集集合的键
     * @return java.lang.Long 返回差集的数量
     */
    public static Long sDifferenceAndStore(Object key ,Object otherkey,Object newKey){
        return redisUtils.getInstance().opsForSet().differenceAndStore(key, otherkey, newKey);
    }

    /**
     * description: 将key集合和collections中的otherKeys集合的差集添加到newkey集合中
     *
     * @param key 集合键
     * @param otherKeys 其他集合的键
     * @param newKey 差集集合的键
     * @return java.lang.Long 返回差集的数量
     */
    public static Long sDifferenceAndStore(Object key,Collection<Object> otherKeys,Object newKey){
        return redisUtils.getInstance().opsForSet().differenceAndStore(newKey, otherKeys, newKey);
    }

    /**
     * description: 删除一个或多个集合中的指定值
     *
     * @param key 集合键
     * @param values 删除的匹配值
     * @return java.lang.Long 成功删除数量
     */
    public static Long sRemove(Object key,Object... values){
        return redisUtils.getInstance().opsForSet().remove(key, values);
    }

    /**
     * description: 随机移除一个元素,并返回出来
     *
     * @param key 集合键
     * @return java.lang.Object 删除的值
     */
    public static Object sRandomPop(Object key){
        return redisUtils.getInstance().opsForSet().pop(key);
    }

    /**
     * description: 随机获取一个元素
     *
     * @param key 集合键
     * @return java.lang.Object 获取的值
     */
    public static Object sRandom(Object key){
        return redisUtils.getInstance().opsForSet().randomMember(key);
    }

    /**
     * description: 随机获取指定数量的元素,同一个元素可能会选中两次
     *
     * @param key 集合键
     * @param count 获取数量
     * @return java.util.List<java.lang.Object> 获取的值的集合
     */
    public static List<Object> sRandom(Object key,long count){
        return redisUtils.getInstance().opsForSet().randomMembers(key, count);
    }

    /**
     * description: 随机获取指定数量的元素,去重(同一个元素只能选择两一次)
     *
     * @param key 集合键
     * @param count 获取数量
     * @return java.util.Set<java.lang.Object> 获取的值的集合
     */
    public static Set<Object> sRandomDistinct(Object key,long count){
        return redisUtils.getInstance().opsForSet().distinctRandomMembers(key, count);
    }

    /**
     * description: 将key集合中的value转入到destKey集合中
     *
     * @param key 集合键
     * @param value 待转移的值
     * @param destKey 另一个集合的键
     * @return boolean 返回成功与否
     */
    public static boolean sMoveSet(Object key,Object value,Object destKey){
        return redisUtils.getInstance().opsForSet().move(key, value, destKey);
    }

    /**
     * description: 返回key集合的大小
     *
     * @param key 集合键
     * @return java.lang.Long 集合大小
     */
    public static Long sSize(Object key){
        return redisUtils.getInstance().opsForSet().size(key);
    }

    /**
     * description: 判断key集合中是否有value
     *
     * @param key 集合键
     * @param value 待查找的值
     * @return boolean 是否存在于key集合中
     */
    public static boolean isMember(Object key,Object value){
        return redisUtils.getInstance().opsForSet().isMember(key, value);
    }

    /**
     * description: 返回key集合和otherKey集合的并集
     *
     * @param key 集合键
     * @param otherKey 另一个集合的键
     * @return java.util.Set<java.lang.Object> 并集
     */
    public static Set<Object> sUnion(Object key,Object otherKey){
        return redisUtils.getInstance().opsForSet().union(key, otherKey);
    }

    /**
     * description: 返回key集合和otherKey集合的并集
     *
     * @param key 集合键
     * @param otherKey 其他集合的键
     * @return java.util.Set<java.lang.Object> 并集
     */
    public static Set<Object> sUnion(Object key,Collection<Object> otherKeys){
        return redisUtils.getInstance().opsForSet().union(key, otherKeys);
    }

    /**
     * description: 将key集合和otherKey集合的并集存入destKey集合中
     *
     * @param key 集合键
     * @param otherKey 另一个集合的键
     * @param destKey 并集的键
     * @return java.lang.Long 返回destKey集合的数量
     */
    public static Long sUnionAndStore(Object key, Object otherKey,Object destKey){
        return redisUtils.getInstance().opsForSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * description: 将key集合与otherKeys集合的并集,保存到destKey集合中
     *
     * @param key 集合键
     * @param otherKeys 其他集合的键
     * @param destKey 并集的键
     * @return java.lang.Long 返回destKey的数量
     */
    public static Long sUnionAndStore(Object key, Collection<Object> otherKeys,Object destKey){
        return redisUtils.getInstance().opsForSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * description: 返回key集合中所有元素
     *
     * @param key 集合键
     * @return java.util.Set<java.lang.Object> key集合的所有值
     */
    public static Set<Object> sMembers(Object key){
        return redisUtils.getInstance().opsForSet().members(key);
    }

    //========================sorted set（有序集合）操作===========================

    /**
     * description: 添加操作
     *
     * @param key 键
     * @param value 值
     * @param score 分数（依次进行排序）
     * @return boolean 是否成功
     */
    public static boolean zAdd(Object key,Object value,double score){
        return redisUtils.getInstance().opsForZSet().add(key, value, score);
    }

    /**
     * description: 批量添加
     *
     * @param key 键
     * @param tuples 值
     * @return java.lang.Long 插入成功的数量
     */
    public static Long zBatchAdd(Object key,Set<ZSetOperations.TypedTuple<Object>> tuples){
        return redisUtils.getInstance().opsForZSet().add(key, tuples);
    }

    /**
     * description: 删除key有序集合中值为values的元素
     *
     * @param key 键
     * @param values 值
     * @return java.lang.Long 删除成功的数量
     */
    public static Long zRemove(Object key,Object... values){
        return redisUtils.getInstance().opsForZSet().remove(key, values);
    }

    /**
     * description: 获取key中指定value的排名(从0开始,从小到大排序)
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Long 排名
     */
    public static Long zRank(Object key,Object value){
        return redisUtils.getInstance().opsForZSet().rank(key, value);
    }

    /**
     * description: 获取key中指定value的排名(从0开始,从大到小排序)
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Long 排名
     */
    public static Long zReverseRank(Object key,Object value){
        return redisUtils.getInstance().opsForZSet().reverseRank(key, value);
    }

    /**
     * description: 获取索引区间内的排序结果集合(从0开始,从小到大,带上分数)
     *
     * @param key 键
     * @param start 开始区间
     * @param end 结束区间
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(Object key, long start, long end){
        return redisUtils.getInstance().opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * description: 获取索引区间内的排序结果集合(从0开始,从小到大,只有列名)
     *
     * @param key 键
     * @param start 开始区间
     * @param end 结束区间
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> zRange(Object key, long start, long end){
        return redisUtils.getInstance().opsForZSet().range(key, start, end);
    }

    /**
     * description: 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,只有列名)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return java.util.Set<java.lang.Object> 排序结果
     */
    public static Set<Object> zRangeByScore(Object key, double min, double max){
        return redisUtils.getInstance().opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * description: 获取分数范围内的 [min,max] 的排序结果集合 (从小到大,集合带分数)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(Object key, double min, double max){
        return redisUtils.getInstance().opsForZSet().rangeByScoreWithScores(key, min, max);
    }

    /**
     * description: 返回[min，max]分数范围内指定count数量的元素集合, 并且从offset下标开始(从小到大,不带分数的集合)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> zRangeByScore(Object key, double min, double max,long offset,long count){
        return redisUtils.getInstance().opsForZSet().rangeByScore(key, min, max, offset, count);
    }

    /**
     * description: 返回[min，max]分数范围内指定count数量的元素集合, 并且从offset下标开始(从小到大,带分数的集合)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(Object key, double min, double max, long offset, long count){
        return redisUtils.getInstance().opsForZSet().rangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * description: 获取索引区间内的排序结果集合(从0开始,从大到小,只有列名)
     *
     * @param key 键
     * @param start 开始区间
     * @param end 结束区间
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> zReverseRange(Object key,long start,long end){
        return redisUtils.getInstance().opsForZSet().reverseRange(key, start, end);
    }

    /**
     * description: 获取索引区间内的排序结果集合(从0开始,从大到小,带上分数)
     *
     * @param key 键
     * @param start 开始区间
     * @param end 结束区间
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(Object key,long start,long end){
        return redisUtils.getInstance().opsForZSet().reverseRangeWithScores(key, start, end);
    }

    /**
     * description: 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合不带分数)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> zReverseRangeByScore(Object key,double min,double max){
        return redisUtils.getInstance().opsForZSet().reverseRangeByScore(key, min, max);
    }

    /**
     * description: 获取分数范围内的 [min,max] 的排序结果集合 (从大到小,集合带分数)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(Object key,double min,double max){
        return redisUtils.getInstance().opsForZSet().reverseRangeByScoreWithScores(key, min, max);
    }

    /**
     * description: 返回[min，max]分数范围内指定count数量的元素集合, 并且从offset下标开始(从大到小,不带分数的集合)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return java.util.Set<java.lang.Object>
     */
    public static Set<Object> zReverseRangeByScore(Object key,double min,double max,long offset,long count){
        return redisUtils.getInstance().opsForZSet().reverseRangeByScore(key, min, max, offset, count);
    }

    /**
     * description: 返回[min，max]分数范围内指定count数量的元素集合, 并且从offset下标开始(从大到小,带分数的集合)
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @param offset 从指定下标开始
     * @param count 输出指定元素数量
     * @return java.util.Set<org.springframework.data.redis.core.ZSetOperations.TypedTuple<java.lang.Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(Object key,double min,double max,long offset,long count){
        return redisUtils.getInstance().opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
    }

    /**
     * description: 返回指定分数区间[min,max]的元素个数
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return long 元素个数
     */
    public static long zCount(Object key,double min,double max){
        return redisUtils.getInstance().opsForZSet().count(key, min, max);
    }

    /**
     * description: 返回key有序集合的元素数量
     *
     * @param key 键
     * @return long 元素数量
     */
    public static long zSize(Object key){
        return redisUtils.getInstance().opsForZSet().size(key);
    }

    /**
     * description: 获取指定成员value的score值
     *
     * @param key 键
     * @param value 值
     * @return java.lang.Double score的值
     */
    public static Double zGetScore(Object key,Object value){
        return redisUtils.getInstance().opsForZSet().score(key, value);
    }

    /**
     * description: 删除指定索引位置的成员,其中成员分数按( 从小到大 )
     *
     * @param key 键
     * @param start 开始区间
     * @param end 结束区间
     * @return java.lang.Long 删除的个数
     */
    public static Long zRemoveRange(Object key,long start ,long end){
        return redisUtils.getInstance().opsForZSet().removeRange(key, start, end);
    }

    /**
     * description: 删除指定分数范围[min,max]内的成员,其中成员分数按( 从小到大 )
     *
     * @param key 键
     * @param min score开始区间
     * @param max score结束区间
     * @return java.lang.Long 删除的个数
     */
    public static Long zRemoveRangeByScore(Object key,double min ,double max){
        return redisUtils.getInstance().opsForZSet().removeRangeByScore(key, min, max);
    }

    /**
     * description: key和other两个集合的并集,保存在destKey集合中，列名相同的score相加
     *
     * @param key 键
     * @param otherKey 键
     * @param destKey 并集的键
     * @return java.lang.Long
     */
    public static Long zUnionAndStore(Object key,Object otherKey,Object destKey){
        return redisUtils.getInstance().opsForZSet().unionAndStore(key, otherKey, destKey);
    }

    /**
     * description: key和otherKeys多个集合的并集,保存在destKey集合中，列名相同的score相加
     *
     * @param key 键
     * @param otherKey 键
     * @param destKey 并集的键
     * @return java.lang.Long
     */
    public static Long zUnionAndStore(Object key,Collection<Object> otherKeys,Object destKey){
        return redisUtils.getInstance().opsForZSet().unionAndStore(key, otherKeys, destKey);
    }

    /**
     * description: key和otherKey两个集合的交集,保存在destKey集合中
     *
     * @param key 键
     * @param otherKey 键
     * @param destKey 交集的键
     * @return java.lang.Long
     */
    public static Long zIntersectAndStore(Object key,Object otherKey,Object destKey){
        return redisUtils.getInstance().opsForZSet().intersectAndStore(key, otherKey, destKey);
    }

    /**
     * description: key和otherKeys多个集合的交集,保存在destKey集合中
     *
     * @param key 键
     * @param otherKey 键
     * @param destKey 交集的键
     * @return java.lang.Long
     */
    public static Long zIntersectAndStore(Object key, Collection<Object> otherKeys, Object destKey){
        return redisUtils.getInstance().opsForZSet().intersectAndStore(key, otherKeys, destKey);
    }
}