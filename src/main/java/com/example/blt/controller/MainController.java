package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.ConsoleVo;
import com.example.blt.service.BLTService;
import com.example.blt.service.CacheableService;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@RestController
public class MainController {

    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Resource
    private GuavaCacheManager guavaCacheManager;
    @Resource
    private BLTService blTservice;

    @RequestMapping("/switch")
    public String console(ConsoleVo consoleVo) {
        String info = JSON.toJSONString(consoleVo);
        ControlTask task = new ControlTask(info);
        String result = ExecuteTask.sendCmd(task);
        return result;
    }

    @RequestMapping("/test")
    public String ping(ConsoleVo consoleVo) {
        List<String> ips = blTservice.getHosts();
        String host = consoleVo.getHost();
        String info = JSON.toJSONString(consoleVo);
        ControlTask task = new ControlTask(info);
        String result = ExecuteTask.sendCmd(task);
        int index = consoleVo.getCommand().indexOf("7701011B");
        if (index != -1) {
            if ("all".equals(host)) {
                ExecuteTask.ping(false, 1, ips.toArray(new String[ips.size()]));
            } else {
                ExecuteTask.ping(false, 1, host);
            }
        }
        return result;
    }

    @RequestMapping(value = "/sendSocket4", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket4(String host, String command) {
        host = blTservice.getHostId(host);
        Map<String, String> map = new HashMap<>();
        String result;
        if (command.equalsIgnoreCase("ON")) {
            //开
            command = "77010315373766";
        } else if (command.equalsIgnoreCase("OFF")) {
            //关
            command = "77010315323266";
        }
        map.put("host", host);
        map.put("command", command);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        result = ExecuteTask.sendCmd(task);
        map.put("success", result);
        return map;
    }

    @RequestMapping(value = "/sendSocket5", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket5(String host, String command) {
        host = blTservice.getHostId(host);
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
        } else if (command.equals("场景1")) {
            command = "7701021901";
        } else if (command.equals("场景2")) {
            command = "7701021902";
        } else if (command.equals("场景3")) {
            command = "7701021903";
        } else if (command.equals("场景4")) {
            command = "7701021904";
        } else if (command.equals("场景5")) {
            command = "7701021905";
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
        host = blTservice.getHostId(host);
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


    @RequestMapping(value = "/sendSocket7", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket7(@RequestParam("commands") List<String> commands, String host) {
        host = blTservice.getHostId(host);
        Map<String, String> map = new HashMap<>();
        String success = "success";
        for (int i = 0; i < commands.size(); i++) {
//        String code1 = SocketUtil.sendCmd2(host, cmd1);
            map.put("command", commands.get(i));
            map.put("host", host);
            ControlTask task = new ControlTask(JSON.toJSONString(map));
            String code = ExecuteTask.sendCmd(task);
            if ("fail".equals(code)) {
//            失败
                success = "error";
                break;
            }
        }
        map.put("success", success);
        return map;
    }

    @Resource
    private CacheableService cacheableService;

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
