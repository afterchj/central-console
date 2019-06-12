package com.example.blt;

import com.example.blt.netty.ServerMain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableCaching//开启缓存注解
public class CentralConsoleApplication {


	public static void main(String[] args) {
//		ApplicationContext ac = new AnnotationConfigApplicationContext(CentralConsoleApplication.class);
		SpringApplication.run(CentralConsoleApplication.class, args);
		new ServerMain().run(8001);
		new ServerMain().run(8000);
//		ac.getBean(ServerMain.class).run(8001);
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
