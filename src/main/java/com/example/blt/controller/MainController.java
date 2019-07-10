package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.Monitor2Dao;
import com.example.blt.entity.vo.ConsoleVo;
import com.example.blt.service.CacheableService;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.SocketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@RestController
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/test")
    public String ping() {
        ExecuteTask.pingStatus(false,1);
        return "ok";
    }

    @RequestMapping("/switch")
    public String console(ConsoleVo consoleVo) {
        String info = JSON.toJSONString(consoleVo);
        ControlTask task = new ControlTask(info);
        String result = ExecuteTask.sendCmd(task);
        return result;
    }

    @RequestMapping(value = "/sendSocket4", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket4(String host, String command) {
        String info;
        Map<String, String> map = new HashMap<>();
        String result = "success";
        String host1 = "192.168.1.191";//茶室
        String host2 = "192.168.1.192";//活动室
        String host3 = "192.168.1.193";//客餐厅
        String host4 = "192.168.1.195";//洽谈室
        String host5 = "192.168.1.194";//办公大厅
        if (command.equalsIgnoreCase("ON")) {
            //开
            command = "77010315373766";
            map.put("command", command);
        } else if (command.equalsIgnoreCase("OFF")) {
            //关
            command = "77010315323266";
            map.put("command", command);
        }
        if (host.equals("all")) {
            //向所有地址发信息
            map.put("host", "all");
            info = JSON.toJSONString(map);
            ControlTask task = new ControlTask(info);
            result = ExecuteTask.sendCmd(task);
        } else {
//            String cmd = host + ":" + command;
            map.put("host", host);
            info = JSON.toJSONString(map);
            ControlTask task = new ControlTask(info);
            result = ExecuteTask.sendCmd(task);
        }
        map.put("success", result);
        return map;
    }

    @RequestMapping(value = "/sendSocket", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        if ("开".equals(command)) {
            command = "1";
        } else if ("关".equals(command)) {
            command = "2";
        }
        String cmd = host + ":" + command;
        logger.warn("cmd=" + command);
        String code = SocketUtil.sendCmd2(host, cmd);
        if ("1".equals(code)) {
//            失败
            success = "error";
        }
        map.put("success", success);
        return map;
    }

    @RequestMapping(value = "/sendSocket2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket2(String host1, String host2, String command) {
        Map<String, String> map = new HashMap<>();
        String success1 = "success";
        String success2 = "success";
        if ("开".equals(command)) {
            command = "1";
        } else if ("关".equals(command)) {
            command = "2";
        }
        logger.warn("cmd=" + command);
        String cmd1 = host1 + ":" + command;
        String cmd2 = host2 + ":" + command;
        String code1 = SocketUtil.sendCmd2(host1, cmd1);
        String code2 = SocketUtil.sendCmd2(host2, cmd2);
        if ("1".equals(code1)) {
//            失败
            success1 = "error";
        }
        if ("1".equals(code2)) {
//            失败
            success2 = "error";
        }
        map.put("success1", success1);
        map.put("success2", success2);
        return map;
    }

    @RequestMapping(value = "/sendSocket3", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket3(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        String cmd = host + ":" + command;
        logger.warn("cmd=" + command);
        String code = SocketUtil.sendCmd2(host, cmd);
        if ("1".equals(code)) {
//            失败
            success = "error";
        }
        map.put("success", success);
        return map;
    }

//    @RequestMapping(value = "/sendSocket4", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> sendSocket4(String host, String command) {
//        Map<String, String> map = new HashMap<>();
//        String success = "success";
//        String host1 = "192.168.1.191";//茶室
//        String host2 = "192.168.1.192";//活动室
//        String host3 = "192.168.1.193";//客餐厅
//        String host4 = "192.168.1.195";//洽谈室
//        String host5 = "192.168.1.194";//办公大厅
//        if (command.equalsIgnoreCase("ON")) {
//            //开
//            command = "77010315373766";
//        } else if (command.equalsIgnoreCase("OFF")) {
//            //关
//            command = "77010315323266";
//        }
//        if (host.equals("all")) {
//            //向所有地址发信息
//            String cmd1 = host1 + ":" + command;
//            String cmd2 = host2 + ":" + command;
//            String cmd3 = host3 + ":" + command;
//            String cmd4 = host4 + ":" + command;
//            String cmd5 = host5 + ":" + command;
//            String code1 = SocketUtil.sendCmd2(host1, cmd1);
//            String code2 = SocketUtil.sendCmd2(host2, cmd2);
//            String code3 = SocketUtil.sendCmd2(host3, cmd3);
//            String code4 = SocketUtil.sendCmd2(host4, cmd4);
//            String code5 = SocketUtil.sendCmd2(host5, cmd5);
//            if ("1".equals(code1) || "1".equals(code2) || "1".equals(code3) || "1".equals(code4) || "1".equals(code5)) {
//                //            失败
//                success = "error";
//            }
//        } else {
//            String cmd = host + ":" + command;
//            String code = SocketUtil.sendCmd2(host, cmd);
//            if ("1".equals(code)) {
//                success = "error";
//            }
//        }
//        map.put("success", success);
//        return map;
//    }

    @RequestMapping(value = "/sendSocket5", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket5(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        if (command.equals("场景一")) {
            command = "7701021901";
        } else if (command.equals("场景二")) {
            command = "7701021902";
        } else if (command.equals("场景三")) {
            command = "7701021903";
        } else if (command.equals("场景四")) {
            command = "7701021904";
        } else if (command.equals("场景五")) {
            command = "7701021905";
        } else if (command.equals("场景六")) {
            command = "7701021906";
        } else if (command.equals("场景七")) {
            command = "7701021907";
        } else if (command.equals("场景八")) {
            command = "7701021908";
        }
        String cmd = host + ":" + command;
        map.put("command", command);
        map.put("host", host);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
//        SocketUtil.sendCmd2(host, cmd);
        map.put("success", success);
        return map;
    }

    @RequestMapping(value = "/sendSocket6", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket6(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        String cmd1 = host + ":" + command;
//        String code1 = SocketUtil.sendCmd2(host, cmd1);
        map.put("command", command);
        map.put("host", host);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        String code = ExecuteTask.sendCmd(task);
        if ("fail".equals(code)) {
//            失败
            success = "error";
        }
        map.put("success", success);
        return map;
    }

//    private static Cache<String, String> cache;
//    static {
//         cache = CacheBuilder.newBuilder()
//                .maximumSize(100) // 设置缓存的最大容量
//                .concurrencyLevel(10) // 设置并发级别为10
//                .recordStats() // 开启缓存统计
//                .build();
//    }

    @Resource
    private CacheableService cacheableService;

    @Resource
    private GuavaCacheManager guavaCacheManager;

    @RequestMapping(value = "/getMsgByMF", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getMsgByMF(@RequestBody String params) {

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        System.out.println(params);
        JSONObject jsonObject = JSONObject.parseObject(params);
        String msg = jsonObject.getString("msg");
        String type = jsonObject.getString("type");
        Cache cache = guavaCacheManager.getCache("msg");
        cache.put(type, msg);
        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("msg").get(type);
        System.out.println(new Date() + " : " + valueWrapper.get());
        return map;
    }

    @RequestMapping(value = "/getMsgByMF2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> getMsgByMF2(@RequestBody String params) {

        Map<String, String> map = new HashMap<>();
        map.put("result", "success");
        System.out.println(params);
        JSONObject jsonObject = JSONObject.parseObject(params);
        String msg = jsonObject.getString("msg");
        String type = jsonObject.getString("type");
        cacheableService.setCache(type, msg);
//        System.out.println(new Date()+" : "+msg);
//        Cache cache = guavaCacheManager.getCache("msg");
//        cache.put(type,msg);
        msg = cacheableService.getCache(type);
        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("msg").get(type);
        System.out.println(new Date() + " : " + msg);
        return map;
    }

}
