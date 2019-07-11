package com.example.blt;

import com.example.blt.netty.ServerMain;
import com.example.blt.socket.NettyServer;
import com.example.blt.utils.FrameSpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
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
     *
     * @return
     */
    @Bean
    public FrameSpringBeanUtil frameSpringBeanUtil() {
        return new FrameSpringBeanUtil();
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
