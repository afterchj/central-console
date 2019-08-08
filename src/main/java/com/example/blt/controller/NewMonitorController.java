package com.example.blt.controller;

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
    private NewMonitorService newMonitorService;

    @Resource
    private NewMonitorDao newMonitorDao;

    /**
     * 主体内容
     * @param floor,type 0巡检 1事件驱动
     * @return
     */
    @RequestMapping(value = "/getNewMonitor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewMonitor(String type,String floor) {
//        long start = System.currentTimeMillis();
        Map<String, Object> map = new HashMap<>();
        List<String> exception = newMonitorService.getException(type);
        List<LightDemo> lightState = newMonitorService.getIntelligenceLightInfo(type);
        List<CenterException> mnames = webCmdDao.getMnames();
        List<Map<String, Object>> centerLNumList = newMonitorService.getIntelligenceCenterLNum(type);
        List<Map<String, Object>> centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception);
        Map<String, Object> indexFloorStatus = new HashMap<>();
        Map<String,Object> floorStatus = new HashMap<>();

        if ("index".equals(floor)){
            //首页
            indexFloorStatus = newMonitorService.getIndexFloorStatus(type);
        }else {
            //指定楼层
            lightState = newMonitorService.getFloorLights(type,floor);
            floorStatus = newMonitorService.getFloorLightsStatus(type,lightState,floor);

        }
        map.put("leftFloors", centerLNums);//左侧导航栏状态
        map.put("indexFloorStatus", indexFloorStatus);//首页
        map.put("floorStatus",floorStatus);//指定楼层
//        long end = System.currentTimeMillis();
//        System.out.println(end-start);
        return map;

    }
    @RequestMapping(value = "/getLeft", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getLeft(String type) {
        Map<String, Object> map = new HashMap<>();
        List<String> exception = newMonitorService.getException(type);
        List<Map<String, Object>> centerLNumList = newMonitorService.getIntelligenceCenterLNum(type);
        List<CenterException> mnames = webCmdDao.getMnames();
        List<LightDemo> lightState = newMonitorService.getIntelligenceLightInfo(type);
        List<Map<String, Object>> centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception);
        map.put("leftFloors", centerLNums);//左侧导航栏状态
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
                exception.stream().filter(exp -> ms.get("mname").equals(exp)).forEach(exp -> ms.put("exception", "1"));
//                for (String exp: exception){
//                    if (ms.get("mname").equals(exp)){
//                        ms.put("exception", "1");
//                    }
//                }
            }
            if (centerLNumList.size()>0){
                centerLNumList.stream().filter(centerNum -> centerNum.get("mname").equals(ms.get("mname"))).forEach
                        (centerNum -> ms.put("centerLNum", centerNum.get("centerLNum")));
//                for (Map<String, Object> centerNum : centerLNumList) {
//                    if (centerNum.get("mname").equals(ms.get("mname"))){
//                        ms.put("centerLNum", centerNum.get("centerLNum"));
//                    }
//
//                }
            }
            mnames.stream().filter(mname -> mname.getMname().equals(ms.get("mname"))).forEach(mname -> {
                int on = mname.getOn();
                int off = mname.getOff();
                if (off == 1 && on == 1) {
                    ms.put("diff", "1");
                }
            });

//            for (CenterException mname : mnames) {
//                if (mname.getMname().equals(ms.get("mname"))) {
//                    int on = mname.getOn();
//                    int off = mname.getOff();
//                    if (off == 1&&on == 1) {
//                        ms.put("diff", "1");
//                    }
//                }
//            }
            centerLNums.add(ms);
        }
        return centerLNums;
    }
}