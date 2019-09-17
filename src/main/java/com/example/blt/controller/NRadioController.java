package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.example.blt.service.BLTService;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-17 13:27
 **/
@Controller
@RequestMapping("/nradio")
public class NRadioController {

    static {
        Map<Integer, String> colors = new HashMap<>();
        int colorsCount = 20;
        for (int i = 0; i <= 100; i = i + 5) {
            String str = String.valueOf(colorsCount);
            if (colorsCount<10){
                str = "0"+str;
            }
            colors.put(i,str);
            colorsCount = colorsCount - 1;
        }
        Map<Integer, String> luminances = new HashMap<>();
        int luminancesCount = 19;
        for (int i = 0; i <= 100; i = i + 5) {
            String str = String.valueOf(luminancesCount);
            if (luminancesCount<10){
                str = "0"+str;
            }
            if (i == 100){
                str = "00";
            }
            luminances.put(i,str);
            luminancesCount = luminancesCount - 1;
        }
    }
    @Resource
    private BLTService blTservice;

    @RequestMapping("/send")
    @ResponseBody
    public String send(String host,String cmd){
        host = blTservice.getHostId(host);
        Map<String, String> map = new HashMap<>();
        map.put("command", cmd);
        map.put("host", host);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
        System.out.println("host: "+host+" cmd: "+cmd);
        return "success";
    }


}
