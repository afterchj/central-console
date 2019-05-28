package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.ConsoleInfo;
import com.example.blt.utils.SocketUtil;
import com.whalin.MemCached.MemCachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@RestController
public class MainController {
    @Autowired
    private MemCachedClient memCachedClient;

    private Logger logger = LoggerFactory.getLogger(MainController.class);


    @RequestMapping("/switch")
    public String console(ConsoleInfo consoleInfo) {
        String info = JSON.toJSONString(consoleInfo);
        SocketUtil.sendCmd(info);
        logger.info("info=" + info);
        return "ok";
    }

    @RequestMapping("/getMemcacheAddress")
    public Map<String, String> getMemcacheAddress(String address) {
        address = "central-console" + address;
        String value = (String) memCachedClient.get(address);
//        System.out.println("value: " + value);
        Map<String, String> map = new HashMap<>();
        if (value == null) {
            value = "1";
        } else {
            value = "0";
        }
        map.put("value", value);
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
        logger.info("cmd=" + command);
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
        logger.info("cmd=" + command);
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

}
