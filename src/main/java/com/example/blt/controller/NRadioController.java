//package com.example.blt.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.example.blt.service.BLTService;
//import com.example.blt.task.ControlTask;
//import com.example.blt.task.ExecuteTask;
//import com.example.blt.utils.DimmingUtil;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-09-17 13:27
// **/
//@Controller
//@RequestMapping("/nradio")
//public class NRadioController {
//    static Map<Integer, String> colors;
//    static Map<Integer, String> luminances;
//    static {
//        colors = DimmingUtil.toAddHexList("colors");
//        luminances = DimmingUtil.toAddHexList("luminances");
//    }
//    @Resource
//    private BLTService blTservice;
//
//    @RequestMapping("/send")
//    @ResponseBody
//    public String send(String host,String cmd){
////        host = blTservice.getHostId(host);
//        Map<String, String> map = new HashMap<>();
//        map.put("command", cmd);
//        map.put("host", host);
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        ExecuteTask.sendCmd(task);
//        System.out.println("host: "+host+" cmd: "+cmd);
//        return "success";
//    }
//
//    @RequestMapping("/change")
//    @ResponseBody
//    public String change(Integer color, Integer luminance ){
////        String host = blTservice.getHostId("192.168.10.22");
//        Map<String, String> map = new HashMap<>();
//        String cmd = "77010315"+colors.get(color)+luminances.get(luminance)+"66";
//        map.put("command", cmd);
//        map.put("host", "all");
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        ExecuteTask.sendCmd(task);
//        System.out.println("host: "+"all"+" cmd: "+cmd);
//        return "success";
//    }
//}
