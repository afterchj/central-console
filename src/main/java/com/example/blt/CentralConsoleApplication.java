package com.example.blt;

import com.example.blt.netty.ServerMain;
import com.example.blt.socket.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching//开启缓存注解
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
//	@Bean
//	public FrameSpringBeanUtil frameSpringBeanUtil(){
//		return new FrameSpringBeanUtil();
//	}
}
