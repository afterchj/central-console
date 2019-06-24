package com.example.blt.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.LightListDao;
import com.example.blt.entity.LightDemo;
import org.springframework.cache.Cache;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

    @Resource
    private LightListDao lightListDao;

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

    @RequestMapping("/monitor")
    public String monitor(Model model){
        List<Map<String,Object>> centerLNumList = lightListDao.getCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getPlaceLNum();
        List<LightDemo> lightState = lightListDao.getLightInfo();
//        model.addAttribute("centerLState","");//每个楼层灯的总开关状态
//        model.addAttribute("placeLState","");//每个区域灯的总开关状态
        model.addAttribute("centerLNumList",centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList",placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState",lightState);//所有灯的状态

        return "monitor";
    }

    @RequestMapping("/monitor2")
    public String monitor2(Model model){
        List<Map<String,Object>> centerLNumList = lightListDao.getOfficeCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getOfficePlaceLNum();
        List<LightDemo> lightState = lightListDao.getOfficeLightInfo();
        model.addAttribute("centerLNumList",centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList",placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState",lightState);//所有灯的状态
        return "monitor2";
    }

    @RequestMapping("/monitor3")
    public String monitor3(Model model){
        List<Map<String,Object>> centerLNumList = lightListDao.getExhibitionCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getExhibitionPlaceLNum();
        List<LightDemo> lightState = lightListDao.getExhibitionLightInfo();
        model.addAttribute("centerLNumList",centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList",placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState",lightState);//所有灯的状态
        return "monitor3";
    }

    @RequestMapping(value = "/getMonitor3",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMonitor3(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> centerLNumList = lightListDao.getExhibitionCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getExhibitionPlaceLNum();
        List<LightDemo> lightState = lightListDao.getExhibitionLightInfo();
        map.put("centerLNumList",centerLNumList);
        map.put("placeLNumList",placeLNumList);
        map.put("lightState",lightState);
        return map;
    }

    @RequestMapping(value = "/getMonitor2",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getMonitor2(){
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> centerLNumList = lightListDao.getOfficeCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getOfficePlaceLNum();
        List<LightDemo> lightState = lightListDao.getOfficeLightInfo();
        map.put("centerLNumList",centerLNumList);
        map.put("placeLNumList",placeLNumList);
        map.put("lightState",lightState);
        return map;
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
