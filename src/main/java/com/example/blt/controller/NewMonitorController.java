package com.example.blt.controller;

import com.example.blt.dao.Monitor4Dao;
import com.example.blt.dao.NewMonitorDao;
import com.example.blt.dao.WebCmdDao;
import com.example.blt.entity.CenterException;
import com.example.blt.entity.LightDemo;
import com.example.blt.service.NewMonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-18 13:32
 **/
@Controller
@RequestMapping("/new")
public class NewMonitorController {

    @Resource
    private WebCmdDao webCmdDao;

    @Resource
    private Monitor4Dao monitor4Dao;

    @Resource
    private NewMonitorService newMonitorService;

    @Resource
    private NewMonitorDao newMonitorDao;

    @RequestMapping(value = "/getNewMonitor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewMonitor(String floor) {
        Map<String, Object> map = new HashMap<>();
        List<String> exception = webCmdDao.getException();
        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
        List<CenterException> mnames = webCmdDao.getMnames();
//        List<Mnames> mnames = newMonitorService.getMnames();
//        List<Map<String, Object>> centerLNumList = monitor4Dao.getIntelligenceCenterLNum();
        List<Map<String, Object>> centerLNumList = newMonitorService.getIntelligenceCenterLNum();
        List<Map<String, Object>> centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception);
        Map<String, Object> indexFloorStatus = new HashMap<>();
        Map<String,Object> floorStatus = new HashMap<>();
        if ("index".equals(floor)){
            //首页
            indexFloorStatus = newMonitorService.getIndexFloorStatus(lightState);
        }else {
            lightState = newMonitorService.getFloorLights(floor);
            floorStatus = newMonitorService.getFloorLightsStatus(lightState,floor);

        }
//        List<LightDemo> placeLNumList = monitor4Dao.getIntelligencePlaceLNum();
//        Map statusMap = getSwitchStatus(lightState);
        map.put("leftFloors", centerLNums);//左侧导航栏状态
        map.put("indexFloorStatus", indexFloorStatus);
//        map.put("placeLNumList", placeLNumList);
//        map.put("lightState", lightState);
//        map.put("status", statusMap);
        return map;
    }




    public List<Map<String, Object>> getLeftCenter(List<LightDemo> lightState, List<CenterException> mnames,
                                                   List<Map<String, Object>> centerLNumList, List<String> exception) {
        List<Map<String, Object>> centerLNums = new ArrayList<>();
        List<Map<String, Object>> mnamesByLeft = newMonitorDao.getMnamesByLeft();
        for (int i = 0; i < lightState.size(); i++) {
            String lightStateMname = lightState.get(i).getMname();
            int lightStatePlace = lightState.get(i).getPlace();
            int lightStateGroupId = lightState.get(i).getGroupId();
            String lightStateStatus = lightState.get(i).getStatus();
            for (int j = 0; j < mnames.size(); j++) {
                if (lightStateStatus != null) {
                    String mname = mnames.get(j).getMname();
                    int place = mnames.get(j).getPlace();
                    int groupId = mnames.get(j).getGroupId();
                    if (mname.equals(lightStateMname) && place == lightStatePlace && groupId == lightStateGroupId) {
                        if ("0".equals(lightState.get(i).getStatus())) {
                            mnames.get(j).setOn(1);
                        } else if ("1".equals(lightStateStatus)) {
                            mnames.get(j).setOff(1);
                        }
                    }
                }
            }
        }
        for (Map<String, Object> ms:mnamesByLeft){
            ms.put("exception", "0");
            ms.put("centerLNum", 0);
            ms.put("diff", "0");
            if (exception.size()>0){
                for (String exp: exception){
                    if (ms.get("mname").equals(exp)){
                        ms.put("exception", "1");
                    }
                }
            }
            if (centerLNumList.size()>0){
                for (Map<String, Object> centerNum : centerLNumList) {
                    if (centerNum.get("mname").equals(ms.get("mname"))){
                        ms.put("centerLNum", centerNum.get("centerLNum"));
                    }

                }
            }
            for (CenterException mname : mnames) {
                if (mname.getMname().equals(ms.get("mname"))) {
                    if (mname.getOff() == 1&&mname.getOn() == 1) {
                        ms.put("diff", "1");
                    }
                }
            }
            centerLNums.add(ms);

        }
        return centerLNums;
    }
}
