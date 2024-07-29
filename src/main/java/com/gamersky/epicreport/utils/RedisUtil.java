package com.gamersky.epicreport.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import com.gamersky.epicreport.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Redis工具类
 *
 * @author : wangshijie
 * @version : 1.0
 */
@Slf4j
@SuppressWarnings("unused")
@Component
public class RedisUtil {

    private static RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_EXCEPTION = "Redis Exception：";


    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key) {
        long aLong = 0L;
        try {
            aLong = Optional.ofNullable(redisTemplate.getExpire(key, TimeUnit.SECONDS)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return aLong;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key) {
        try {
            return Optional.ofNullable(redisTemplate.hasKey(key)).orElse(false);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... key) {
        try {
            if (key != null && key.length > 0) {
                if (key.length == 1) {
                    redisTemplate.delete(key[0]);
                } else {
                    redisTemplate.delete(CollUtil.toList(key));
                }
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        try {
            return key == null ? null : redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return null;
    }

    /**
     * 普通缓存获取
     *
     * @param key       键
     * @param beanClass 缓存类型
     * @return 值
     */
    public static <T> T get(String key, Class<T> beanClass) {
        try {
            Object o = redisTemplate.opsForValue().get(key);
            if (o != null) {
                return Convert.convert(beanClass, o);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return null;
    }

    /**
     * 普通缓存获取
     *
     * @param key           键
     * @param typeReference 缓存类型
     * @return 值
     */
    public static <T> T get(String key, TypeReference<T> typeReference) {
        Object o = redisTemplate.opsForValue().get(key);
        if (o != null) {
            return Convert.convert(typeReference, o);
        }
        return null;
    }


    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void set(String key, T value, Integer timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     */
    public static void set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 递增
     *
     * @param key   键
     * @param delta 要增加几(大于0)
     */
    public static long incr(String key, long delta) {
        try {
            if (delta < 0) {
                throw new ServiceException("递增因子必须大于0");
            }
            return Optional.ofNullable(redisTemplate.opsForValue().increment(key, delta)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     */
    public static long decr(String key, long delta) {
        try {
            if (delta < 0) {
                throw new ServiceException("递减因子必须大于0");
            }
            return Optional.ofNullable(redisTemplate.opsForValue().increment(key, -delta)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    // ================================Map=================================

    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hGet(String key, String item) {
        try {
            return redisTemplate.opsForHash().get(key, item);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return null;
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object, Object> hmGet(String key) {
        try {
            return redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptyMap();
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     */
    public static void hmSet(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     */
    public static void hmSet(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     */
    public static void hSet(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     */
    public static void hSet(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hDel(String key, Object... item) {
        try {
            redisTemplate.opsForHash().delete(key, item);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item) {
        try {
            return redisTemplate.opsForHash().hasKey(key, item);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return false;
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     */
    public static double hIncr(String key, String item, double by) {
        try {
            return redisTemplate.opsForHash().increment(key, item, by);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0.0D;
    }

    /**
     * hash递减
     *
     * @param key  键
     * @param item 项
     * @param by   要减少记(小于0)
     */
    public static double hDecr(String key, String item, double by) {
        try {
            return redisTemplate.opsForHash().increment(key, item, -by);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0.0D;
    }

    // ============================set=============================

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public static Set<Object> sGet(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public static <T> Set<T> sGet(String key, Class<T> beanClass) {
        try {
            Set<Object> members = redisTemplate.opsForSet().members(key);
            if (members != null && CollUtil.isNotEmpty(members)) {
                return members.stream().map(o -> Convert.convert(beanClass, o)).collect(Collectors.toSet());
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key, Object value) {
        try {
            Set<Object> objects = sGet(key);
            if (Objects.nonNull(objects) && CollUtil.isNotEmpty(objects)) {
                return objects.parallelStream().filter(o -> Objects.equals(o, value))
                        .anyMatch(o -> redisTemplate.opsForSet().isMember(key, o));
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return false;
    }

    /**
     * 将数据放入set缓存
     *
     * @param key    键
     * @param values 值 可以是多个
     */
    public static void sSet(String key, Set<?> values) {
        try {
            redisTemplate.opsForSet().add(key, values.toArray());
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     */
    public static void sSetAndTime(String key, long time, Set<?> values) {
        try {
            redisTemplate.opsForSet().add(key, values.toArray());
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public static long sGetSetSize(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForSet().size(key)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key   键
     * @param value 值
     */
    public static void setRemove(String key, Object value) {
        try {
            Set<Object> objects = sGet(key);
            if (Objects.nonNull(objects) && CollUtil.isNotEmpty(objects)) {
                objects.parallelStream().filter(o -> Objects.equals(o, value))
                        .forEach(o -> redisTemplate.opsForSet().remove(key, o));
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }
    // ===============================list=================================

    /**
     * 获取list缓存的内容
     *
     * @param key 键
     */
    public static List<Object> lGet(String key) {
        try {
            return redisTemplate.opsForList().range(key, 0, -1);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public static List<Object> lGet(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取list缓存的内容
     *
     * @param key       键
     * @param beanClass 缓存类型
     */
    public static <T> List<T> lGet(String key, Class<T> beanClass) {
        try {
            List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
            if (range != null && CollUtil.isNotEmpty(range)) {
                return range.stream().map(o -> Convert.convert(beanClass, o)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取list缓存的内容
     *
     * @param key       键
     * @param beanClass 缓存类型
     * @param start     开始
     * @param end       结束 0 到 -1代表所有值
     */
    public static <T> List<T> lGet(String key, Class<T> beanClass, long start, long end) {
        try {
            List<Object> range = redisTemplate.opsForList().range(key, start, end);
            if (range != null && CollUtil.isNotEmpty(range)) {
                return range.stream().map(o -> Convert.convert(beanClass, o)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptyList();
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public static long lGetListSize(String key) {
        try {
            return Optional.ofNullable(redisTemplate.opsForList().size(key)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static Object lGetIndex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
            return null;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key       键
     * @param beanClass 缓存类型
     * @param index     索引 index>0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public static <T> T lGetIndex(String key, Class<T> beanClass, long index) {
        try {
            Object o = redisTemplate.opsForList().index(key, index);
            if (o != null) {
                return Convert.convert(beanClass, o);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return null;
    }

    /**
     * 向list最右边添加元素
     *
     * @param key   键
     * @param value 值
     */
    public static void lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 向list最右边添加元素
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public static void lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 向list最右边添加元素集合
     *
     * @param key   键
     * @param value 值
     */
    public static <T> void lSet(String key, List<T> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value.toArray());
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 向list最右边添加元素集合
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public static <T> void lSet(String key, List<T> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value.toArray());
            if (time > 0) {
                expire(key, time);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 在list的指定位置插入元素,如果指定位置已有元素，则覆盖，没有则新增，超过集合下标+n则会报错。
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public static void lUpdateIndex(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 从list中删除等于value的所有元素
     *
     * @param key   键
     * @param value 值
     */
    public static void lRemove(String key, Object value) {
        try {
            List<Object> objects = lGet(key);
            if (Objects.nonNull(objects) && CollUtil.isNotEmpty(objects)) {
                objects.parallelStream().filter(o -> Objects.equals(o, value)).forEach(o ->
                        redisTemplate.opsForList().remove(key, 0, o));
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 从list中删除等于value的元素的第一个计数事件。
     * count> 0：删除等于从左到右移动的值的count个元素；
     * count< 0：删除等于从右到左移动的值的count个元素；
     * count = 0：删除等于value的所有元素。
     *
     * @param key   键
     * @param count 计数
     * @param value 值
     */
    public static void lRemove(String key, long count, Object value) {
        try {
            List<Object> objects = lGet(key);
            if (Objects.nonNull(objects) && CollUtil.isNotEmpty(objects)) {
                objects.parallelStream().filter(o -> Objects.equals(o, value)).forEach(o ->
                        redisTemplate.opsForList().remove(key, count, o));
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 切换数据库
     *
     * @param dbIndex 数据库索引
     */
    public static void setDataBase(int dbIndex) {
        try {
            LettuceConnectionFactory factory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
            if (Objects.nonNull(factory)) {
                RedisStandaloneConfiguration configuration = factory.getStandaloneConfiguration();
                configuration.setDatabase(dbIndex);
                factory.afterPropertiesSet();
                redisTemplate.setConnectionFactory(factory);
            }
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 获取value type类型
     *
     * @param key key
     * @return DataType
     */
    public static DataType getType(String key) {
        try {
            return redisTemplate.type(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return DataType.NONE;
    }

    private RedisUtil() {

    }

    //==============================================================Zset

    /**
     * 添加元素,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value value
     * @param score score
     */
    public static void zAdd(String key, String value, double score) {
        try {
            redisTemplate.opsForZSet().add(key, value, score);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }

    /**
     * 添加元素
     *
     * @param key    key
     * @param values values
     * @return Long
     */
    public static Long zAdd(String key, Set<ZSetOperations.TypedTuple<Object>> values) {
        try {
            return redisTemplate.opsForZSet().add(key, values);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 移除元素
     *
     * @param key    key
     * @param values values
     * @return Long
     */
    public static Long zRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForZSet().remove(key, values);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 增加元素的score值，并返回增加后的值
     *
     * @param key   key
     * @param value value
     * @param delta delta
     * @return Double
     */
    public static Double zIncrementScore(String key, String value, double delta) {
        try {
            return redisTemplate.opsForZSet().incrementScore(key, value, delta);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0.0D;
    }

    /**
     * 返回元素在集合的排名,有序集合是按照元素的score值由小到大排列
     *
     * @param key   key
     * @param value value
     * @return Long，0表示第一位
     */
    public static Long zRank(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().rank(key, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 返回元素在集合的排名,按元素的score值由大到小排列
     *
     * @param key   key
     * @param value value
     * @return Long
     */
    public static Long zReverseRank(String key, Object value) {
        try {
            return Optional.ofNullable(redisTemplate.opsForZSet().reverseRank(key, value)).orElse(0L);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取集合的元素, 从小到大排序
     *
     * @param key   key
     * @param start 开始位置
     * @param end   结束位置, -1查询所有
     * @return Set<String>
     */
    public static Set<Object> zRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 获取集合元素, 并且把score值也获取
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Set<ZSetOperations.TypedTuple < Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return Set<Object>
     */
    public static Set<Object> zRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScore(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key key
     * @param min 最小值
     * @param max 最大值
     * @return Set<ZSetOperations.TypedTuple < Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素, 从小到大排序
     *
     * @param key   key
     * @param min   min
     * @param max   max
     * @param start start
     * @param end   end
     * @return Set<ZSetOperations.TypedTuple < Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zRangeByScoreWithScores(String key, double min, double max,
                                                                                 long start, long end) {
        try {
            return redisTemplate.opsForZSet().rangeByScoreWithScores(key, min, max, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 获取集合的元素, 从大到小排序
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Set<Object>
     */
    public static Set<Object> zReverseRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 获取集合的元素, 从大到小排序, 并返回score值
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Set<ZSetOperations.TypedTuple < Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Set<Object>
     */
    public static Set<Object> zReverseRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Set<ZSetOperations.TypedTuple < Object>>
     */
    public static Set<ZSetOperations.TypedTuple<Object>> zReverseRangeByScoreWithScores(String key, double min,
                                                                                        double max) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();
    }

    /**
     * 根据Score值查询集合元素, 从大到小排序
     *
     * @param key   key
     * @param min   min
     * @param max   max
     * @param start start
     * @param end   end
     * @return Set<Object>
     */
    public static Set<Object> zReverseRangeByScore(String key, double min, double max, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScore(key, min, max, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return Collections.emptySet();

    }

    /**
     * 根据score值获取集合元素数量
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Long
     */
    public static Long zCount(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().count(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return Long
     */
    public static Long zSize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取集合大小
     *
     * @param key key
     * @return Long
     */
    public static Long zZCard(String key) {
        try {
            return redisTemplate.opsForZSet().zCard(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取集合中value元素的score值
     *
     * @param key   key
     * @param value value
     * @return Double
     */
    public static Double zScore(String key, Object value) {
        try {
            return redisTemplate.opsForZSet().score(key, value);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0.0D;
    }

    /**
     * 移除指定索引位置的成员
     *
     * @param key   key
     * @param start start
     * @param end   end
     * @return Long
     */
    public static Long zRemoveRange(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().removeRange(key, start, end);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 根据指定的score值的范围来移除成员
     *
     * @param key key
     * @param min min
     * @param max max
     * @return Long
     */
    public static Long zRemoveRangeByScore(String key, double min, double max) {
        try {
            return redisTemplate.opsForZSet().removeRangeByScore(key, min, max);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     * @return Long
     */
    public static Long zUnionAndStore(String key, String otherKey, String destKey) {
        try {
            return redisTemplate.opsForZSet().unionAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 获取key和otherKey的并集并存储在destKey中
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     * @return Long
     */
    public static Long zUnionAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return redisTemplate.opsForZSet().unionAndStore(key, otherKeys, destKey);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 交集
     *
     * @param key      key
     * @param otherKey otherKey
     * @param destKey  destKey
     * @return Long
     */
    public static Long zIntersectAndStore(String key, String otherKey, String destKey) {
        try {
            return redisTemplate.opsForZSet().intersectAndStore(key, otherKey, destKey);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * 交集
     *
     * @param key       key
     * @param otherKeys otherKeys
     * @param destKey   destKey
     * @return Long
     */
    public static Long zIntersectAndStore(String key, Collection<String> otherKeys, String destKey) {
        try {
            return redisTemplate.opsForZSet().intersectAndStore(key, otherKeys, destKey);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return 0L;
    }

    /**
     * zScan
     *
     * @param key     key
     * @param options options
     * @return Cursor<ZSetOperations.TypedTuple < Object>>
     */
    public static Cursor<ZSetOperations.TypedTuple<Object>> zScan(String key, ScanOptions options) {
        try {
            return redisTemplate.opsForZSet().scan(key, options);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return null;
    }

    //==============================================================分布式锁

    //加锁失效时间，毫秒
    private static int LOCK_EXPIRE = 1000; // ms

    /**
     * 加锁
     *
     * @param key key
     * @return boolean
     */
    @SuppressWarnings("unchecked,rawtypes")
    public static boolean tryLock(String key) {
        try {
            Object obj = redisTemplate.execute((RedisCallback) connection -> {
                //获取时间毫秒值
                long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
                //获取锁
                Boolean acquire = connection.setNX(key.getBytes(), String.valueOf(expireAt).getBytes());
                if (Boolean.TRUE.equals(acquire)) {
                    return true;
                } else {
                    byte[] bytes = connection.get(key.getBytes());
                    //非空判断
                    if (Objects.nonNull(bytes) && bytes.length > 0) {
                        long expireTime = Long.parseLong(new String(bytes));
                        // 如果锁已经过期
                        if (expireTime < System.currentTimeMillis()) {
                            // 重新加锁，防止死锁
                            byte[] set = connection.getSet(key.getBytes(),
                                    String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                            assert set != null;
                            return Long.parseLong(new String(set)) < System.currentTimeMillis();
                        }
                    }
                }
                return false;
            });
            return (boolean) Optional.ofNullable(obj).orElse(false);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
        return false;
    }

    /**
     * 解锁
     *
     * @param key key
     */
    public static void unLock(String key) {
        try {
            redisTemplate.delete(key);
        } catch (Exception e) {
            log.warn(REDIS_EXCEPTION, e);
        }
    }


    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置有效时间
     *
     * @param key     Redis键
     * @param timeout 超时时间
     * @param unit    时间单位
     * @return true=设置成功；false=设置失败
     */
    public static boolean expire(String key, long timeout, TimeUnit unit) {
        return redisTemplate.expire(key, timeout, unit);
    }


    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
}
