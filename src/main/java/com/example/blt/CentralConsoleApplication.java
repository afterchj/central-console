package com.example.blt;

import com.example.blt.netty.ServerMain;
import com.example.blt.socket.NettyServer;
import com.example.blt.utils.FrameSpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching//开启缓存注解
//@MapperScan("com.example.blt.dao")//mybatis路径映射
//@ComponentScan(basePackages = "org.example.blt")
public class CentralConsoleApplication {


	public static void main(String[] args) {
		SpringApplication.run(CentralConsoleApplication.class, args);
		new NettyServer().start(8000);
		new ServerMain().run(8001);
	}

	/**
	 * 开启心跳微服务
	 * @return
	 */
	@Bean
	public FrameSpringBeanUtil frameSpringBeanUtil(){
		return new FrameSpringBeanUtil();
	}

}
