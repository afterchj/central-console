package com.example.blt.controller;

import com.example.blt.dao.LightListDao;
import com.example.blt.dao.Monitor2Dao;
import com.example.blt.dao.Monitor4Dao;
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
import java.util.concurrent.atomic.AtomicInteger;

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

    @Resource
    private Monitor4Dao monitor4Dao;

    //    private int commandId = 0;
//    private int newCommandId = 0;
    //Integer原子操作
    private AtomicInteger commandId = new AtomicInteger(0);
    private AtomicInteger newCommandId = new AtomicInteger(0);

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

    @RequestMapping(value = "/getMonitor2", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor2() {
        int id = monitor2Dao.getCommandId("16");
        commandId.compareAndSet(0, id);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> centerLNumList = lightListDao.getOfficeCenterLNum();
        List<LightDemo> placeLNumList = lightListDao.getOfficePlaceLNum();
        List<LightDemo> lightState = lightListDao.getOfficeLightInfo();
        map.put("centerLNumList", centerLNumList);
        map.put("placeLNumList", placeLNumList);
        map.put("lightState", lightState);
        return map;
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

    @RequestMapping(value = "/getNewMonitor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewMonitor() {
        int id = monitor2Dao.getCommandId("16");
        commandId.compareAndSet(0, id);
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> centerLNumList = monitor4Dao.getIntelligenceCenterLNum();
        List<LightDemo> placeLNumList = monitor4Dao.getIntelligencePlaceLNum();
        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
        Map statusMap = getSwitchStatus(lightState);
        map.put("centerLNumList", centerLNumList);
        map.put("placeLNumList", placeLNumList);
        map.put("lightState", lightState);
        map.put("status", statusMap);
        return map;
    }


    @RequestMapping(value = "/getNewMonitorLightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewMonitorLightStatus() {
        Map<String, Object> map = new HashMap<>();
        String scenes = null;
        CommandLight commandInfo = monitor4Dao.getCommandInfo("16");
//        List<LightDemo> placeLNumList = monitor4Dao.getPlaceLNum("intelligence");
//        List<LightDemo> centerLNumList = monitor4Dao.getCenterLNum("intelligence");
        if (commandInfo != null) {
//            int id = commandInfo.getId();
//            if (newCommandId.get() < id) {
//                newCommandId.set(id);
//                newCommandId = id;
            LightDemo lightDemo = new LightDemo();
            String ctype = commandInfo.getCtype();
            String status = null;
            if ("52".equals(ctype)) {
                //遥控器
                if ("01".equals(commandInfo.getCid())) {
                    //全开
                    status = "0";
                } else if ("02".equals(commandInfo.getCid())) {
                    //全关
                    status = "1";
                }
                lightDemo.setMname(getMname(commandInfo.getHost()));
                lightDemo.setStatus(status);
                lightDemo.setOther("floor");
            } else if ("C0".equals(ctype)) {
                //pad or 手机 全控
                if ("32".equals(commandInfo.getY())) {
                    //全关
                    status = "1";
                } else {
                    //全开
                    status = "0";
                }
                lightDemo.setMname(getMname(commandInfo.getHost()));
                lightDemo.setStatus(status);
                lightDemo.setOther("floor");
            } else if ("C1".equals(ctype)) {
                //pad or 手机 组控
                int groupId;
                if ("0A".equals(commandInfo.getCid())) {
                    groupId = 10;
                } else {
                    groupId = Integer.valueOf(commandInfo.getCid());
                }
                if ("37".equals(commandInfo.getY())) {
                    status = "0";
                } else {
                    status = "1";
                }
                lightDemo.setMname(getMname(commandInfo.getHost()));
                lightDemo.setGroupId(groupId);
                lightDemo.setStatus(status);
                lightDemo.setOther("group");
                int place = monitor4Dao.getPlace(lightDemo.getMname(), groupId);
                List<Integer> statusList1 = monitor4Dao.getStatusOfPlace(lightDemo.getMname(), place, groupId);
                List<Integer> statusList2 = monitor4Dao.getStatusOfFloor(lightDemo.getMname(), place, groupId);
                Map map1 = getExceptionAndDiff(statusList1);
                Map map2 = getExceptionAndDiff(statusList2);
                LightDemo placeStatus = new LightDemo();
                LightDemo floorStatus = new LightDemo();
                placeStatus.setMname(lightDemo.getMname());
                placeStatus.setPlace(place);
                placeStatus.setOther("开");
                floorStatus.setMname(lightDemo.getMname());
                floorStatus.setOther("开");
                if(status.equals("1")){
                    if(getSwitchStatus2(statusList1)==0){
                        placeStatus.setOther("关");
                        if(getSwitchStatus2(statusList2)==0){
                            floorStatus.setOther("关");
                        };
                    };
                }
                map.put("placeStatus", placeStatus);
                map.put("floorStatus", floorStatus);
                map.put("place", place);
                map.put("placeException", map1.get("exception"));
                map.put("placeDifference", map1.get("difference"));
                map.put("floorException", map2.get("exception"));
                map.put("floorDifference", map2.get("difference"));
            } else if ("42".equals(ctype)) {
                if ("01".equals(commandInfo.getCid())) {
                    scenes = "场景一";
                } else if ("02".equals(commandInfo.getCid())) {
                    scenes = "场景二";
                } else if ("03".equals(commandInfo.getCid())) {
                    scenes = "场景三";
                } else if ("04".equals(commandInfo.getCid())) {
                    scenes = "场景四";
                }
            }
            map.put("lightDemo", lightDemo);
//            }
//            map.put("placeLNumList", placeLNumList);
//            map.put("centerLNumList", centerLNumList);
            map.put("scenes", scenes);
        }
        return map;
    }




    @RequestMapping(value = "/getLightOnOrOff", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getLightOnOrOff() {
        Map<String, Object> map = new HashMap<>();
        List<LightDemo> lightState = new ArrayList<>();
//        map.put("lightState", lightState);
        CommandLight commandInfo = monitor4Dao.getCommandInfo("1");
        if (commandInfo != null) {
            String ctype = commandInfo.getCtype();
            String status = null;
            if ("52".equals(ctype)) {
                //遥控器
                if ("01".equals(commandInfo.getCid())) {
                    //全开
                    status = "0";
                } else if ("02".equals(commandInfo.getCid())) {
                    //全关
                    status = "1";
                }
                lightState = monitor2Dao.getMonitorFromRemoteByStatus(status, "exhibition");
            } else if ("C0".equals(ctype)) {
                //pad or 手机 全控
                if ("37".equals(commandInfo.getY())) {
                    //全开
                    status = "0";
                } else if ("32".equals(commandInfo.getY())) {
                    //全关
                    status = "1";
                }
                lightState = monitor2Dao.getMonitorFromRemoteByStatus(status, "exhibition");
            } else if ("C1".equals(ctype)) {
                //pad or 手机 组控
                int groupId;
                if ("0A".equals(commandInfo.getCid())) {
                    groupId = 10;
                } else {
                    groupId = Integer.valueOf(commandInfo.getCid());
                }
                if ("37".equals(commandInfo.getY())) {
                    status = "0";
                } else {
                    status = "1";
                }
                lightState = monitor4Dao.getMonitorFromPhoneByGroup(groupId, status, "exhibition");
            }
            map.put("lightState", lightState);
        }
        return map;
    }


    @RequestMapping(value = "/getMonitor2LightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor2LightStatus() {
        List<LightDemo> lightState2 = new ArrayList<>();
        int id = monitor2Dao.getCommandId("16");
        CommandLight commandInfo = monitor4Dao.getCommandInfo("16");
        List<LightDemo> placeLNumList = monitor2Dao.getPlaceLNum("Office");
        Map<String, Object> map = new HashMap<>();
        String scenes = null;
        if (commandInfo != null) {
            if (commandId.get() < id) {
                commandId.set(id);
//                commandId = id;
                String status = null;
                String ctype = commandInfo.getCtype();
                if ("52".equals(commandInfo.getCtype())) {
                    //遥控器
                    if ("01".equals(commandInfo.getCid())) {
                        //全开
                        status = "0";
                    } else if ("02".equals(commandInfo.getCid())) {
                        //全关
                        status = "1";
                    }
                    lightState2 = monitor2Dao.getMonitorFromRemoteByStatus(status, "Office");

                } else if ("C0".equals(commandInfo.getCtype())) {
                    //pad or 手机 全控
                    if ("32".equals(commandInfo.getY())) {
                        //全关
                        status = "1";
                    } else {
                        //全开
                        status = "0";
                    }
                    lightState2 = monitor2Dao.getMonitorFromRemoteByStatus(status, "Office");
                } else if ("C1".equals(commandInfo.getCtype())) {
                    //pad or 手机 组控
                    int groupId;
                    if ("0A".equals(commandInfo.getCid())) {
                        groupId = 10;
                    } else {
                        groupId = Integer.valueOf(commandInfo.getCid());
                    }

                    if ("32".equals(commandInfo.getY())) {
                        status = "1";
                    } else {
                        status = "0";
                    }
                    lightState2 = monitor4Dao.getMonitorFromPhoneByGroup(groupId, status, "Office");
                } else if ("42".equals(ctype)) {
                    if ("01".equals(commandInfo.getCid())) {
                        scenes = "场景一";
                    } else if ("02".equals(commandInfo.getCid())) {
                        scenes = "场景二";
                    } else if ("03".equals(commandInfo.getCid())) {
                        scenes = "场景三";
                    } else if ("04".equals(commandInfo.getCid())) {
                        scenes = "场景四";
                    }
                }

            }
            map.put("lightState", lightState2);
            map.put("placeLNumList", placeLNumList);
            map.put("centerLNumList", 84);
            map.put("scenes", scenes);
        }
        return map;
    }


    @RequestMapping(value = "/getMonitorLightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitorLightStatus() {
        Map<String, Object> map = new HashMap<>();
        List<LightDemo> lightState = new ArrayList<>();
        CommandLight commandInfo = monitor4Dao.getCommandInfo("16");
        if (commandInfo != null) {
            String status = null;
            String ctype = commandInfo.getCtype();
            if ("52".equals(ctype)) {
                //遥控器
                if ("01".equals(commandInfo.getCid())) {
                    //全开
                    status = "0";
                } else if ("02".equals(commandInfo.getCid())) {
                    //全关
                    status = "1";
                }
                lightState = monitorDao.getMonitorFromRemoteByStatus(status);

            } else if ("C0".equals(ctype)) {
                //pad or 手机 全控
                if ("37".equals(commandInfo.getY())) {
                    //全开
                    status = "0";
                } else if ("32".equals(commandInfo.getY())) {
                    //全关
                    status = "1";
                }
                lightState = monitorDao.getMonitorFromRemoteByStatus(status);
            } else if ("C1".equals(ctype)) {
                //pad or 手机 组控
                int groupId = Integer.valueOf(commandInfo.getCid());
//                String status;
                if ("37".equals(commandInfo.getY())) {
                    status = "0";
                } else {
                    status = "1";
                }
                lightState = monitorDao.getMonitorFromPhoneByGroup(groupId, status);
            }
            map.put("lightState", lightState);
        }
        return map;
    }

    private String getMname(String host) {
        String mname = "";
        switch (host) {
            case "192.168.16.103":
                mname = "1楼";
                break;
            case "192.168.16.68":
                mname = "2楼";
                break;
            case "192.168.16.66":
                mname = "3楼";
                break;
            case "192.168.16.70":
                mname = "4楼";
                break;
            case "192.168.16.71":
                mname = "5楼";
                break;
            case "192.168.16.72":
                mname = "6楼";
                break;
            case "192.168.16.73":
                mname = "7楼";
                break;
            case "192.168.16.80":
                mname = "8楼";
                break;
            case "192.168.16.79":
                mname = "9楼";
                break;
            case "192.168.16.76":
                mname = "10楼";
                break;
        }
        return mname;
    }

    private Map<String, Boolean> getExceptionAndDiff(List<Integer> statusList) {
        Map<String, Boolean> map = new HashMap<>();
        Boolean exception = false;
        Boolean difference = false;
        int[][] arr = new int[statusList.size() / 4][4];
        int index1 = -1;
        int index2 = 0;
        for (int i = 0; i < statusList.size(); i++) {
            if (i % 4 == 0) {
                index1++;
                index2 = 0;
            }
            if (statusList.get(i) != null) {
                arr[index1][index2] = statusList.get(i);
                index2++;
            } else {
                exception = true;
                arr[index1][index2] = 2;
                index2++;
            }
        }
        int checkStatus = -1;
        for (int i = 0; i <= index1; i++) {
            for (int j = 0; j < 4; j++) {
                if (checkStatus == -1) {
                    checkStatus = arr[i][j];
                }
                if (checkStatus != arr[i][j] && arr[i][j] != 2) {
                    difference = true;
                    break;
                }
            }
            if (difference) {
                break;
            }
            checkStatus = -1;
        }
        map.put("exception", exception);
        map.put("difference", difference);
        return map;
    }

    private Map<String,List<LightDemo>> getSwitchStatus(List<LightDemo> lightState) {
        Map<String,List<LightDemo>> map = new HashMap<>();
        List<LightDemo> groupStatus = new ArrayList<>();
        List<LightDemo> placeStatus = new ArrayList<>();
        List<LightDemo> floorStatus = new ArrayList<>();
        List<LightDemo> allStatus = new ArrayList<>();
        int groupFlag = 0;
        int placeFlag = 0;
        int floorFlag = 0;
        int allFlag = 0;
        for (int i = 0; i < lightState.size(); i++) {
            if (i % 4 == 0) {
                groupFlag = 0;
            }
            if (i % 12 == 0) {
                placeFlag = 0;
            }
            if (i % 48 == 0) {
                floorFlag = 0;
            }
            if (lightState.get(i).getStatus() != null) {
                if (Integer.parseInt(lightState.get(i).getStatus()) == 0) {
//                            lightState.get(i).setOther(lightState.get(i).getGroupId() + "_" + 0);
                    groupFlag = 1;
                    placeFlag = 1;
                    floorFlag = 1;
                    allFlag = 1;
                }
            }
            if ((i + 1) % 4 == 0 && groupFlag == 0) {
                LightDemo lightDemo = new LightDemo();
                lightDemo.setMname(lightState.get(i).getMname());
                lightDemo.setPlace(lightState.get(i).getPlace());
                lightDemo.setGroupId(lightState.get(i).getGroupId());
                lightDemo.setOther("关");
                groupStatus.add(lightDemo);
            }
            if ((i + 1) % 12 == 0 && placeFlag == 0) {
                LightDemo lightDemo = new LightDemo();
                lightDemo.setMname(lightState.get(i).getMname());
                lightDemo.setPlace(lightState.get(i).getPlace());
                lightDemo.setOther("关");
                placeStatus.add(lightDemo);
            }
            if ((i + 1) % 48 == 0 && floorFlag == 0) {
                LightDemo lightDemo = new LightDemo();
                lightDemo.setMname(lightState.get(i).getMname());
                lightDemo.setOther("关");
                floorStatus.add(lightDemo);
            }
        }
        if(allFlag==0){
            LightDemo lightDemo = new LightDemo();
            lightDemo.setOther("关");
            allStatus.add(lightDemo);
        }
        map.put("groupStatus",groupStatus);
        map.put("placeStatus",placeStatus);
        map.put("floorStatus",floorStatus);
        map.put("allStatus",allStatus);
        return map;
    }

    private int getSwitchStatus2(List<Integer> statusList) {
        int placeFlag = 0;
        for(int i=0;i<statusList.size();i++){
            if(statusList.get(i)!=null){
                if(statusList.get(i)==0){
                    placeFlag = 1;
                }
            }
        }
        return placeFlag;
    }


}
