package com.example.blt.config;

import com.whalin.MemCached.MemCachedClient;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-05-21 11:39
 **/
@Component
public class WebSocketTask {

    private final static String IP1 = "192.168.16.103";
    private final static String IP2 = "192.168.16.70";
    private MemCachedClient memCachedClient;
    private WebSocket webSocket = new WebSocket();
//    private String valueIp1 = "";
//    private String valueIp2 = "";

    /**
     * 一秒钟查询一次
     */
    @Scheduled(cron = "0/1 * * * * *")
    public void backSearch() {
        String addressIp1 = "central-console" + IP1;
        String addressIp2 = "central-console" + IP2;
        memCachedClient = new MemCachedClient();
        String newValueIp1 = (String) memCachedClient.get(addressIp1);
        String newValueIp2 = (String) memCachedClient.get(addressIp2);
        String value;
        if (newValueIp1 == null) {
            newValueIp1 = "1";
        } else {
            newValueIp1 = "0";
        }
        if (newValueIp2 == null) {
            newValueIp2 = "1";
        } else {
            newValueIp2 = "0";
        }
        value = newValueIp1+":"+newValueIp2;
        webSocket.sendMessage(value);
    }
}
