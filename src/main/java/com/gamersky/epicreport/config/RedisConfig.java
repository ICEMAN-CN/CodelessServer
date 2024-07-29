package com.gamersky.epicreport.config;

import cn.hutool.core.util.ReflectUtil;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.gamersky.epicreport.exception.ServiceException;
import com.gamersky.epicreport.utils.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisSystemException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * Redis配置
 *
 * @author : aliberter
 * @version : 1.0
 */
@Slf4j
@Configuration
@AllArgsConstructor
public class RedisConfig extends CachingConfigurerSupport {

    private final RedisConnectionFactory redisConnectionFactory;

    @PostConstruct
    public void initRedisCache() {
        String key = "init";
        String errorMsg = "Redis connect failed!";
        try {
            ReflectUtil.setFieldValue(RedisUtil.class, "redisTemplate", redisTemplate(redisConnectionFactory));
            RedisUtil.set(key, 1, 10);
            int value = Optional.ofNullable(RedisUtil.get(key, Integer.class)).orElse(0);
            if (value != 1) {
                throw new ServiceException(errorMsg);
            }
            log.info("====== Redis started ======");
        } catch (Exception e) {
            throw new RedisSystemException(errorMsg, e);
        }
    }


    /**
     * redisTemplate
     *
     * @param redisConnectionFactory factory
     * @return RedisTemplate<String, Object>
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        factory.setShareNativeConnection(false);
        redisTemplate.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}