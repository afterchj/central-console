package com.example.blt;

import com.example.blt.utils.FrameSpringBeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableScheduling
@EnableCaching//开启缓存注解
//@MapperScan("com.example.blt.dao")//mybatis路径映射
//@ComponentScan(basePackages = "org.example.blt")
@EnableTransactionManagement
public class CentralConsoleApplication {

    private static Logger logger = LoggerFactory.getLogger(CentralConsoleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CentralConsoleApplication.class, args);
    }

    /**
     * 开启心跳微服务
     *
     * @return
     */
    @Bean
    public FrameSpringBeanUtil frameSpringBeanUtil() {
        return new FrameSpringBeanUtil();
    }

    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

//    @Bean
//    public WebSocketClient webSocketClient() {
//        try {
//            WSClient webSocketClient = new WSClient(new URI
//                    ("ws://122.112.229.195:8083/ws/webSocket"));
////            WSClient webSocketClient = new WSClient(new URI
////                    ("ws://localhost:8083/ws/webSocket"));
//            webSocketClient.connect();
//            return webSocketClient;
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


}
