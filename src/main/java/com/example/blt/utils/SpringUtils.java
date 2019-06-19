package com.example.blt.utils;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by hongjian.chen on 2019/1/22.
 */
public class SpringUtils {

    private static ApplicationContext ctx = null;

    static {
        ctx = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
    }

    public static SqlSessionTemplate getSqlSession() {
        return (SqlSessionTemplate) ctx.getBean("mySqlSessionTemplate");
    }

    public static RedisTemplate getRedisTemplate() {
        return (RedisTemplate) ctx.getBean("redisTemplate");
    }

//    public static AmqpTemplate getAmqpTemplate() {
//        return (AmqpTemplate) ctx.getBean("amqpTemplate");
//    }

//    public static MessageProducer getProducer() {
//        return ctx.getBean(MessageProducer.class);
//    }

    public static void main(String[] args) {
        System.out.println("redisTemplate="+getRedisTemplate().opsForValue().get("test"));
    }
}
