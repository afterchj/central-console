package com.example.blt;

import com.example.blt.netty.ServerMain;
import com.example.blt.utils.FrameSpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CentralConsoleApplication {


	public static void main(String[] args) {
		SpringApplication.run(CentralConsoleApplication.class, args);
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
