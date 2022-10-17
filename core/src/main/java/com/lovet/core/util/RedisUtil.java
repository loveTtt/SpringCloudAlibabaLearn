package com.lovet.core.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Log4j2
@Component
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return BooleanUtil.isTrue(redisTemplate.hasKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void del(String key) {
        try {
            if (key != null) {
                redisTemplate.delete(key);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }

    /**
     * 放入缓存
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public void set(String key,Object value,long timeout, TimeUnit unit){
        try {
            redisTemplate.opsForValue().set(key,value,timeout,unit);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }



    /**
     * 自增器
     * @param key 自增key
     * @param liveTime 存活时间，按小时计算
     * @return 自增后的数
     */
    public Long incr(String key, long liveTime) {
        long increment = 0;
        try {
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            increment = entityIdCounter.getAndIncrement();
            if (increment == 0 && liveTime > 0) {
                entityIdCounter.expire(liveTime, TimeUnit.HOURS);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return increment;
    }

    /**
     * 根据自增key获取自增数
     * @param key 自增key
     * @return 自增数
     */
    public Long getIncrement(String key) {
        long increment = 0L;
        try {
            RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, Objects.requireNonNull(redisTemplate.getConnectionFactory()));
            increment = entityIdCounter.get();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return increment;
    }


    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Object obj = null;
        try {
            if(StringUtils.isNotBlank(key)){
                obj = redisTemplate.opsForValue().get(key);
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return obj;
    }

    /**
     * 向队列中push数据
     * @param key 队列的key
     * @param param push的数据
     */
    public void listLeftPush(String key, String param) {
        redisTemplate.opsForList().leftPush(key, param);
    }

    /**
     * 根据前缀批量删除key
     * @param prefix key前缀
     */
    public void delKeys(String prefix) {
        try {
            Set<String> keys = redisTemplate.keys(prefix.concat("*"));
            if (CollUtil.isNotEmpty(keys)) {
                redisTemplate.delete(keys);
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
    }
}
