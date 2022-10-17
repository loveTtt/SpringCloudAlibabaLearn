package com.lovet.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();

        template.setConnectionFactory(factory);

        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();

        // key采用String的序列化方式
        template.setKeySerializer(jdkSerializationRedisSerializer);

        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(jdkSerializationRedisSerializer);

        // value序列化方式采用jackson
        template.setValueSerializer(jdkSerializationRedisSerializer);

        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(jdkSerializationRedisSerializer);

        template.afterPropertiesSet();

        return template;
    }
}
