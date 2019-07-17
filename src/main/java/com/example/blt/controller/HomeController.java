package com.example.blt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@Controller
public class HomeController {

//    @Autowired
//    private MemCachedClient memCachedClient;

//    @Resource
//    private WebSocket webSocket;

    @Resource
    private RedisTemplate redisTemplate;

    @RequestMapping("/")
    public String index() {
        return "index";
    }

//
//    @RequestMapping("/newIndex")
//    public String newIndex() {
//        return "newMonitor/index";
//    }


    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/index2")
    public String index2() {
        return "index2";
    }

    @RequestMapping("/newIndex")
    public String newIndex() {
        return "newMonitor/index";
    }

    @RequestMapping("/newIndex/noEnergy")
    public String noEnergy() {
        return "newMonitor/noEnergy";
    }

    @Resource
    private GuavaCacheManager guavaCacheManager;

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> get(@RequestBody String params) {
        JSONObject jsonObject = JSONObject.parseObject(params);
        String type = jsonObject.getString("type");
        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("test").get(type);
        if (valueWrapper != null) {
            System.out.println(new Date() + " : " + valueWrapper.get());
        } else {
            System.out.println(new Date() + " : NULL");
        }

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        return map;
    }

    @ResponseBody
    @RequestMapping("/getHost")
    public Map showHost() {
        Map map = new LinkedHashMap();
        ValueOperations<String, Set> operations = redisTemplate.opsForValue();
        Set lmacSet = operations.get(ConsoleKeys.lMAC.getValue());
        Set vaddrSet = operations.get(ConsoleKeys.VADDR.getValue());
        Set ipSet = operations.get(ConsoleKeys.HOSTS.getValue());
        if (null != lmacSet) {
            map.put("lmacSize", lmacSet.size());
            map.put("lmac", lmacSet);

        }
        if (null != vaddrSet) {
            map.put("vaddr", vaddrSet);
            map.put("vaddrSize", vaddrSet.size());
        }
        if (null != ipSet) {
            map.put("hosts", ipSet);
            map.put("hostSize", ipSet.size());
        }
        map.put("result", "ok");
        return map;
    }

}
