package com.example.blt.config;

import com.example.blt.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by hongjian.chen on 2019/6/21.
 */

@Configuration
public class RedisConfiguration implements CachingConfigurer {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Override
    public CacheManager cacheManager() {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(this.redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return stringRedisTemplate;
    }

//    @Bean
//    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter recvAdapter1, MessageListenerAdapter recvAdapter2) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(recvAdapter1, new PatternTopic("channel_cron"));//将订阅者1与channel1频道绑定
//        container.addMessageListener(recvAdapter2, new PatternTopic("channel_cron1"));//将订阅者2与channel1频道绑定
//        return container;
//    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter1, MessageListenerAdapter listenerAdapter2) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        //订阅了一个叫chat的通道
        container.addMessageListener(listenerAdapter1, new PatternTopic("channel_cron"));
        container.addMessageListener(listenerAdapter2, new PatternTopic("channel_cron1"));
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter1(RedisService receiver) {
        //与channel1绑定的适配器,收到消息时执行RedisConsumer类中的consumeMsg方法
        return new MessageListenerAdapter(receiver, "consumeMsg");
    }

    @Bean
    MessageListenerAdapter listenerAdapter2(RedisService receiver) {
        return new MessageListenerAdapter(receiver, "receiverMessage");
    }
}
