package com.hc.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author: 何超
 * @date: 2022/11/15
 * 缓存配置类 放置配置的方法,子模板继承和复用
 */
public class CacheConfiguration {

    //配置缓存manager
    @Bean
    @Primary  //同类型,多个bean,默认生效! 默认缓存时间1小时!  可以选择!
    public RedisCacheManager cacheManagerHour(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration instanceConfig = instanceConfig(1 * 3600L);//缓存时间1小时

        //构建缓存对象
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(instanceConfig)
                .transactionAware()
                .build();
    }

    //缓存一天配置
    @Bean
    public RedisCacheManager cacheManagerDay(RedisConnectionFactory redisConnectionFactory) {

        RedisCacheConfiguration instanceConfig = instanceConfig(24 * 3600L);//缓存时间1小时

        //构建缓存对象
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(instanceConfig)
                .transactionAware()
                .build();
    }


    /**
     * 实例化具体的缓存配置!
     * 设置缓存方式JSON
     * 设置缓存时间 单位秒
     *
     * @param ttl
     * @return
     */
    private RedisCacheConfiguration instanceConfig(Long ttl) {

        //设置jackson序列化工具
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer
                = new Jackson2JsonRedisSerializer<Object>(Object.class);

        //常见jackson的对象映射器,并设置一些基本属性
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(ttl)) //设置缓存时间
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer));
    }
}
