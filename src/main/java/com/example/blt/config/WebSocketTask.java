//package com.example.blt.config;
//
//import com.example.blt.entity.IP;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-05-21 11:39
// **/
//@Component
//public class WebSocketTask {
//
////    private final static String IP1 = "192.168.16.103";
////    private final static String IP2 = "192.168.16.70";
////    private MemCachedClient memCachedClient;
////    @Resource
////    private RedisTemplate<String,String> redisTemplate;
//    private WebSocket webSocket = new WebSocket();
////    private String valueIp1 = "";
////    private String valueIp2 = "";
//
//    /**
//     * 一秒钟查询一次
//     */
//    @Scheduled(cron = "0/1 * * * * *")
//    public void backSearch() {
//        String IP1 = IP.IP103.getValue();
//        String IP2 = IP.IP70.getValue();
//        String addressIp1 = "central-console" + IP1;
//        String addressIp2 = "central-console" + IP2;
////        memCachedClient = new MemCachedClient();
////        String newValueIp1 = (String) memCachedClient.get(addressIp1);
////        String newValueIp2 = (String) memCachedClient.get(addressIp2);
////        redisTemplate = new RedisTemplate<>();
//        Jedis jedis = new Jedis("127.0.0.1",6379);
//        jedis.auth("Tp123456");
//        String newValueIp1 = jedis.get(addressIp1);
//        String newValueIp2 = jedis.get(addressIp2);
//        String value;
//        if (newValueIp1 == null) {
//            newValueIp1 = "1";
//        } else {
//            newValueIp1 = "0";
//        }
//        if (newValueIp2 == null) {
//            newValueIp2 = "1";
//        } else {
//            newValueIp2 = "0";
//        }
//        value = newValueIp1+":"+newValueIp2;
//        webSocket.sendMessage(value);
//    }
//}
