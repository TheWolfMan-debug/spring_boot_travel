package com.wolfman.travel.config.dataConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

/**
 * redis配置
 */
@Configuration
public class MyRedisConfig {
    /**
     * 自定义序列化机制，采用json格式序列化
     *
     * @param factory
     * @return
     */
    @Bean
    RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        //创建默认RedisCacheWriter
        RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);
        //创建默认RedisCacheConfiguration并使用GenericJackson2JsonRedisSerializer构造的		SerializationPair对value进行转换
        //创建GenericJackson2JsonRedisSerializer的json序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        //使用json序列化器构造出对转换Object类型的SerializationPair序列化对
        RedisSerializationContext.SerializationPair<Object> serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer);
        //将可以把Object转换为json的SerializationPair传入RedisCacheConfiguration
        //使得RedisCacheConfiguration在转换value时使用定制序列化器
        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(serializationPair);
        RedisCacheManager cacheManager = new RedisCacheManager(cacheWriter, cacheConfiguration);
        return cacheManager;
    }

}