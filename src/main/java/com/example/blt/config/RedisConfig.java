//package com.example.blt.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.cache.CacheManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.cache.RedisCacheManager;
//import org.springframework.data.redis.cache.RedisCacheWriter;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//
//import java.time.Duration;
//
//@Configuration
//public class RedisConfig {
//    /*缓存管理器配置*/
//    //缓存管理
//    @Bean
//    public CacheManager cacheManager(RedisConnectionFactory
//                                             redisConnectionFactory) {
//        RedisCacheConfiguration
//                redisCacheConfiguration = RedisCacheConfiguration
//                .defaultCacheConfig().entryTtl
//                (Duration.ofHours(1));//// 设置缓存有效期一小时
//        return RedisCacheManager
//                .builder(RedisCacheWriter.nonLockingRedisCacheWriter
//                        (redisConnectionFactory))
//                .cacheDefaults(redisCacheConfiguration).build();
//    }
//    /*设置RedisTemplate*/
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory){
//        StringRedisTemplate template = new StringRedisTemplate(factory);
////        setSerializer(template);//设置序列化工具
//        template.afterPropertiesSet();
//        return template;
//    }
//    /*设置序列化工具*/
////    private void setSerializer(StringRedisTemplate template){
////        @SuppressWarnings({ "rawtypes", "unchecked" })
////        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
////        ObjectMapper om = new ObjectMapper();
////        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
////        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
////        jackson2JsonRedisSerializer.setObjectMapper(om);
////        template.setValueSerializer(jackson2JsonRedisSerializer);
////    }
//}