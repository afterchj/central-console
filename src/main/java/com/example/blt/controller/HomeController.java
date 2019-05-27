package com.example.blt.controller;

import com.example.blt.config.WebSocket;
import com.whalin.MemCached.MemCachedClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@Controller
public class HomeController {

    @Autowired
    private MemCachedClient memCachedClient;

    @Resource
    private WebSocket webSocket;

    private final static String IP1 = "192.168.16.103";

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
//        String address = "central-console"+IP1;
//        String value = (String) memCachedClient.get(address);
//        if (value == null) {
//            value = "1";
//        } else {
//            value = "0";
//        }
//        webSocket.sendMessage(value);
        return "welcome";
    }

}
