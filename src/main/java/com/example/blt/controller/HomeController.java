package com.example.blt.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@Controller
public class HomeController {

//    @Autowired
//    private MemCachedClient memCachedClient;

//    @Resource
//    private WebSocket webSocket;

//    private final static String IP1 = "192.168.16.103";

    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/index2")
    public String index2(){
        return "index2";
    }

    @Resource
    private GuavaCacheManager guavaCacheManager;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> get(@RequestBody String params){
        JSONObject jsonObject = JSONObject.parseObject(params);
        String type = jsonObject.getString("type");
        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("test").get(type);
        if (valueWrapper!=null){
            System.out.println(new Date()+" : "+valueWrapper.get());
        }else {
            System.out.println(new Date()+" : NULL");
        }

        Map<String,String> map = new HashMap<>();
        map.put("result","success");
        return map;
    }

}
