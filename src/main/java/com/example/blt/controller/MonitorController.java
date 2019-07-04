package com.example.blt.controller;

import com.example.blt.dao.LightListDao;
import com.example.blt.dao.Monitor2Dao;
import com.example.blt.dao.MonitorDao;
import com.example.blt.entity.CommandLight;
import com.example.blt.entity.LightDemo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
 * @create: 2019-07-04 15:15
 **/
@Controller
public class MonitorController {

    @Resource
    private LightListDao lightListDao;

    @Resource
    private MonitorDao monitorDao;

    @Resource
    private Monitor2Dao monitor2Dao;

    @RequestMapping("/monitor")
    public String monitor(Model model) {
        List<Map<String, Object>> centerLNumList = lightListDao.getCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getPlaceLNum();
        List<LightDemo> lightState = lightListDao.getLightInfo();
//        model.addAttribute("centerLState","");//每个楼层灯的总开关状态
//        model.addAttribute("placeLState","");//每个区域灯的总开关状态
        model.addAttribute("centerLNumList", centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList", placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState", lightState);//所有灯的状态

        return "monitor";
    }

    @RequestMapping("/monitor2")
    public String monitor2(Model model) {
        List<Map<String, Object>> centerLNumList = lightListDao.getOfficeCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getOfficePlaceLNum();
        List<LightDemo> lightState = lightListDao.getOfficeLightInfo();
        model.addAttribute("centerLNumList", centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList", placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState", lightState);//所有灯的状态
        return "monitor2";
    }

    @RequestMapping("/monitor3")
    public String monitor3(Model model) {
        List<Map<String, Object>> centerLNumList = lightListDao.getExhibitionCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getExhibitionPlaceLNum();
        List<LightDemo> lightState = lightListDao.getExhibitionLightInfo();
        model.addAttribute("centerLNumList", centerLNumList);//每个楼层灯总个数
        model.addAttribute("placeLNumList", placeLNumList);//每个区域的灯个数
        model.addAttribute("lightState", lightState);//所有灯的状态
        return "monitor3";
    }

    @RequestMapping(value = "/getMonitor3", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor3() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> centerLNumList = lightListDao.getExhibitionCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getExhibitionPlaceLNum();
        List<LightDemo> lightState = lightListDao.getExhibitionLightInfo();
        map.put("centerLNumList", centerLNumList);
        map.put("placeLNumList", placeLNumList);
        map.put("lightState", lightState);
        return map;
    }

    @RequestMapping(value = "/getLightOnOrOff", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getLightOnOrOff() {
        Map<String, Object> map = new HashMap<>();
        List<LightDemo> lightState = new ArrayList<>();
//        map.put("lightState", lightState);
        CommandLight commandInfo = lightListDao.getCommandInfo();
        if(commandInfo!=null){
            String ctype = commandInfo.getCtype();
            if ("52".equals(ctype)){
                //遥控器
                if ("01".equals(commandInfo.getCid())){
                    //全开
                    lightState = lightListDao.getExhibitionFromRemoteByOn();
                }else if ("02".equals(commandInfo.getCid())){
                    //全关
                    lightState = lightListDao.getExhibitionFromRemoteByOff();
                }
            }else if ("C0".equals(ctype)){
                //pad or 手机 全控
                if ("37".equals(commandInfo.getY())){
                    //全开
                    lightState = lightListDao.getExhibitionFromRemoteByOn();
                }else if ("32".equals(commandInfo.getY())){
                    //全关
                    lightState = lightListDao.getExhibitionFromRemoteByOff();
                }
            }else if ("C1".equals(ctype)){
                //pad or 手机 组控
                int groupId = Integer.valueOf(commandInfo.getCid());
                String status;
                if ("37".equals(commandInfo.getY())){
                    status="0";
                }else {
                    status="1";
                }
                lightState = lightListDao.getExhibitionFromPhoneByGroup(groupId,status);
            }
            map.put("lightState",lightState);
        }
        return map;
    }


    @RequestMapping(value = "/getMonitorLightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitorLightStatus() {
        Map<String, Object> map = new HashMap<>();
        List<LightDemo> lightState = new ArrayList<>();
        CommandLight commandInfo = monitorDao.getCommandInfo();
        if(commandInfo!=null){
            String status = null;
            String ctype = commandInfo.getCtype();
            if ("52".equals(ctype)){
                //遥控器
                if ("01".equals(commandInfo.getCid())){
                    //全开
                    status="0";
                }else if ("02".equals(commandInfo.getCid())){
                    //全关
                    status="1";
                }
                lightState = monitorDao.getMonitorFromRemoteByStatus(status);

            }else if ("C0".equals(ctype)){
                //pad or 手机 全控
                if ("37".equals(commandInfo.getY())){
                    //全开
                    status="0";
                }else if ("32".equals(commandInfo.getY())){
                    //全关
                    status="1";
                }
                lightState = monitorDao.getMonitorFromRemoteByStatus(status);
            }else if ("C1".equals(ctype)){
                //pad or 手机 组控
                int groupId = Integer.valueOf(commandInfo.getCid());
//                String status;
                if ("37".equals(commandInfo.getY())){
                    status="0";
                }else {
                    status="1";
                }
                lightState = monitorDao.getMonitorFromPhoneByGroup(groupId,status);
            }
            map.put("lightState",lightState);
        }
        return map;
    }

    @RequestMapping(value = "/getMonitor2LightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor2LightStatus() {
        List<LightDemo> lightState2 = new ArrayList<>();
        CommandLight commandInfo = monitor2Dao.getCommandInfo();
        List<LightDemo> placeLNumList = monitor2Dao.getPlaceLNum();
        Map<String, Object> map = new HashMap<>();
        if(commandInfo!=null){
            String status = null;
            String ctype = commandInfo.getCtype();
            if ("52".equals(commandInfo.getCtype())){
                //遥控器
                if ("01".equals(commandInfo.getCid())){
                    //全开
                    status="0";
                }else if ("02".equals(commandInfo.getCid())){
                    //全关
                    status="1";
                }
                lightState2 = monitor2Dao.getMonitorFromRemoteByStatus(status);

            }else if ("C0".equals(commandInfo.getCtype())){
                //pad or 手机 全控
                if ("32".equals(commandInfo.getY())){
                    //全关
                    status="1";
                }else {
                    //全开
                    status="0";
                }
                lightState2 = monitor2Dao.getMonitorFromRemoteByStatus(status);
            }else if ("C1".equals(commandInfo.getCtype())){
                //pad or 手机 组控
                int groupId = Integer.valueOf(commandInfo.getCid());
                if ("32".equals(commandInfo.getY())){
                    status="1";
                }else {
                    status="0";
                }
                lightState2 = monitor2Dao.getMonitorFromPhoneByGroup(groupId,status);
            }
            map.put("lightState",lightState2);
            map.put("placeLNumList",placeLNumList);
        }
        return map;
    }

    @RequestMapping(value = "/getMonitor2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor2() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> centerLNumList = lightListDao.getOfficeCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getOfficePlaceLNum();
        List<LightDemo> lightState = lightListDao.getOfficeLightInfo();
        map.put("centerLNumList", centerLNumList);
        map.put("placeLNumList", placeLNumList);
        map.put("lightState", lightState);
        return map;
    }
}
