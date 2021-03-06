//package com.example.blt.config;
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.blt.dao.LightListDao;
//import com.example.blt.entity.LightDemo;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.List;
//import java.util.Map;
//
///**
// * @program: central-console
// * @description:
// * @author: Mr.Ma
// * @create: 2019-05-21 11:39
// **/
//@Component
//public class WebSocketTask {
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    private WebSocket webSocket = new WebSocket();
//
//    @Resource
//    private LightListDao lightListDao;
//
//    /**
//     * 一秒钟查询一次
//     */
//    @Scheduled(cron = "0/1 * * * * *")
//    public void backSearch() {
//        List<Map<String, Object>> centerLNumList = lightListDao.getCenterLNum();
//        List<LightDemo> placeLNumList = lightListDao.getPlaceLNum();
//        List<LightDemo> lightState = lightListDao.getLightInfo();
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("lightState", lightState);//所有灯的状态
//        jsonObject.put("placeLNumList", placeLNumList);//每个区域的灯个数
//        jsonObject.put("centerLNumList", centerLNumList);//每个楼层灯总个数
////        jsonObject.put("placeLState","");//每个区域灯的总开关状态
////        jsonObject.put("centerLState","lightState");//每个楼层灯的总开关状态
//        webSocket.sendMessage(jsonObject.toJSONString());
//    }
//}
