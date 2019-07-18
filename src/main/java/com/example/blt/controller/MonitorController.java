package com.example.blt.controller;

import com.example.blt.dao.*;
import com.example.blt.entity.CenterException;
import com.example.blt.entity.CommandLight;
import com.example.blt.entity.LightDemo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

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

    @Resource
    private WebCmdDao webCmdDao;

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

    public List<Map<String, Object>> getLeftCenter(List<LightDemo> lightState, List<CenterException> mnames,
                                                   List<Map<String, Object>> centerLNumList, List<String> exception,
                                                   String filterMname, List<LightDemo> lightDemos) {
        List<Map<String, Object>> centerLNums = new ArrayList<>();
        if (filterMname != null) {
            //楼层控制 删除该楼层
            for (Iterator<LightDemo> iter = lightState.iterator(); iter.hasNext(); ) {
                LightDemo ce = iter.next();
                if (filterMname.equals(ce.getMname())) {
                    iter.remove();
                }
            }
            for (Iterator<CenterException> iter = mnames.iterator(); iter.hasNext(); ) {
                CenterException ce = iter.next();
                if (filterMname.equals(ce.getMname())) {
                    iter.remove();
                }
            }
        }
        if (lightDemos.size()>0&&filterMname==null){
            if (lightDemos.size()==1){
                //区域控制
                for (Iterator<LightDemo> iter = lightState.iterator(); iter.hasNext(); ) {
                    LightDemo ce = iter.next();
                    if (lightDemos.get(0).getMname().equals(ce.getMname())&&lightDemos.get(0).getPlace().equals(ce
                            .getPlace())) {
                        iter.remove();
                    }
                }
                for (Iterator<CenterException> iter = mnames.iterator(); iter.hasNext(); ) {
                    CenterException ce = iter.next();
                    if (lightDemos.get(0).getMname().equals(ce.getMname())&&lightDemos.get(0).getPlace().equals(ce
                            .getPlace())) {
                        iter.remove();
                    }
                }
            }else {
                for (Iterator<LightDemo> iter = lightState.iterator(); iter.hasNext(); ) {
                    LightDemo ce = iter.next();
                    for (LightDemo lightDemo:lightDemos){
                        if (lightDemo.getMname().equals(ce.getMname())&&lightDemo.getPlace().equals(ce
                                .getPlace())&&lightDemo.getGroupId().equals(ce.getGroupId())) {
                            iter.remove();
                        }
                    }
                }
                for (Iterator<CenterException> iter = mnames.iterator(); iter.hasNext(); ) {
                    CenterException ce = iter.next();
                    for (LightDemo lightDemo:lightDemos){
                        if (lightDemo.getMname().equals(ce.getMname())&&lightDemo.getPlace().equals(ce
                                .getPlace())&&lightDemo.getGroupId().equals(ce.getGroupId())) {
                            iter.remove();
                        }
                    }
                }
            }
        }

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
                        if ("0".equals(lightStateStatus)) {
                            mnames.get(j).setOn(1);
                        } else if ("1".equals(lightStateStatus)) {
                            mnames.get(j).setOff(1);
                        }
                    }
                }
            }
        }
        for (Map<String, Object> centerNum : centerLNumList) {
            centerNum.put("exception", "0");//默认没有异常
            centerNum.put("diff", "0");
            if (exception.size() > 0) {
                if (exception.contains(centerNum.get("mname"))) {
                    centerNum.put("exception", "1");
                }
            }
            for (CenterException mname : mnames) {
                if (mname.getMname().equals(centerNum.get("mname"))) {
                    if (mname.getOn() == 1 && mname.getOff() == 1) {
                        centerNum.put("diff", "1");
                    }
                }
            }
            centerLNums.add(centerNum);
        }
        return centerLNums;
    }

    @RequestMapping(value = "/getNewMonitor", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getNewMonitor() {
        Map<String, Object> map = new HashMap<>();
        List<String> exception = webCmdDao.getException();
        List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
        List<CenterException> mnames = webCmdDao.getMnames();
        List<Map<String, Object>> centerLNumList = monitor4Dao.getIntelligenceCenterLNum();
        List<LightDemo> lightDemos = new ArrayList<>();
        List<Map<String, Object>> centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception, null, lightDemos);
        List<LightDemo> placeLNumList = monitor4Dao.getIntelligencePlaceLNum();
        Map statusMap = getSwitchStatus(lightState);
        map.put("centerLNumList", centerLNums);//左侧导航栏状态
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
        CommandLight commandInfo = monitor4Dao.getCommandInfo("10");
//        List<LightDemo> placeLNumList = monitor4Dao.getPlaceLNum("intelligence");
//        List<LightDemo> centerLNumList = monitor4Dao.getCenterLNum("intelligence");
        if (commandInfo != null&& !commandInfo.getHost().equals("192.168.10.253")) {
//            int id = commandInfo.getId();
//            if (newCommandId.get() < id) {
//                newCommandId.set(id);
            List<String> exception = webCmdDao.getException();
            List<LightDemo> lightState = monitor4Dao.getIntelligenceLightInfo();
            List<CenterException> mnames = webCmdDao.getMnames();
            List<Map<String, Object>> centerLNumList = monitor4Dao.getIntelligenceCenterLNum();
            List<Map<String, Object>> centerLNums;
//            List<String> exceptionByGroupIdOrPlace = new ArrayList<>();
            List<LightDemo> lightDemos = new ArrayList<>();
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
                lightDemos.add(lightDemo);
            } else if ("C0".equals(ctype)) {
                //pad or 手机 全控
                if ("32".equals(commandInfo.getY())) {
                    //全关
                    status = "1";
                } else {
                    //全开
                    status = "0";
                }
                if ("all".equals(commandInfo.getHost())) {
                    lightDemo.setMname("all");
                    centerLNums = new ArrayList<>();
                    for (Map<String, Object> centerNum : centerLNumList) {
                        centerNum.put("exception", "0");//默认没有异常
                        centerNum.put("diff", "0");
                        centerLNums.add(centerNum);
                    }
                    map.put("centerLNumList", centerLNums);
                } else {
                    lightDemo.setMname(getMname(commandInfo.getHost()));
                    centerLNums = getLeftCenter(lightState, mnames, centerLNumList,
                            exception, lightDemo.getMname(), lightDemos);//左侧导航栏状态
                    map.put("centerLNumList", centerLNums);
                }
                lightDemo.setStatus(status);
                lightDemo.setOther("floor");
                lightDemos.add(lightDemo);

            } else if ("C1".equals(ctype)) {
                //pad or 手机 组控
                if (commandInfo.getCid()!=null){
                    int groupId = Integer.parseInt(commandInfo.getCid(), 16);
//                if ("0A".equals(commandInfo.getCid())) {
//                    groupId = 10;
//                } else {
//                    groupId = Integer.valueOf(commandInfo.getCid());
//                }
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
                    lightDemo.setPlace(place);
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
                    if (status.equals("1")) {
                        if (getSwitchStatus2(statusList1) == 0) {
                            placeStatus.setOther("关");
                            if (getSwitchStatus2(statusList2) == 0) {
                                floorStatus.setOther("关");
                            }
                        }
                    }
                    lightDemos.add(lightDemo);
//                exceptionByGroupIdOrPlace = webCmdDao.getExceptionByGroupId(lightDemo.getGroupId(), lightDemo.getMname(), lightDemo.getPlace());
                    centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception, null, lightDemos);
                    map.put("centerLNumList", centerLNums);
                    map.put("placeStatus", placeStatus);
                    map.put("floorStatus", floorStatus);
                    map.put("placeException", map1.get("exception"));
                    map.put("placeDifference", map1.get("difference"));
                    map.put("floorException", map2.get("exception"));
                    map.put("floorDifference", map2.get("difference"));
                }

            } else if ("42".equals(ctype)) {
                if ("01".equals(commandInfo.getCid())) {
                    scenes = "场景1";
                } else if ("02".equals(commandInfo.getCid())) {
                    scenes = "场景2";
                } else if ("03".equals(commandInfo.getCid())) {
                    scenes = "场景3";
                } else if ("04".equals(commandInfo.getCid())) {
                    scenes = "场景4";
                } else if ("05".equals(commandInfo.getCid())) {
                    scenes = "场景5";
                }
            } else if ("CW".equals(ctype)) {
                //web
                List<CommandLight> webCmds = webCmdDao.getWebCmd();
                String host = webCmds.get(0).getHost();
                String y = webCmds.get(0).getY();
                boolean placeButton = true;//区域按钮
                String cid = webCmds.get(0).getCid();
                int place = monitor4Dao.getPlace(getMname(host), Integer.parseInt(webCmds.get(0).getCid(), 16));

                for (int i = 0; i < webCmds.size(); i++) {
                    if (i != 0) {
                        if (!host.equals(webCmds.get(i).getHost()) || !y.equals(webCmds.get(i).getY()) || cid.equals
                                (webCmds.get(i).getCid())) {
                            placeButton = false;//组按钮
                            break;
                        }
                    }
                }
                if ("37".equals(webCmds.get(0).getY())) {
                    status = "0";
                } else {
                    status = "1";
                }
                List<Integer> statusList1;
                Map<String, Boolean> map1 = new HashMap<>();
                LightDemo placeStatus;

                if (webCmds.size() != 3 || !placeButton) {
                    //单组
                    LightDemo lightDemo1 = new LightDemo();
                    lightDemo1.setGroupId(Integer.parseInt(webCmds.get(0).getCid(), 16));
                    lightDemo1.setStatus(status);
                    lightDemo1.setOther("group");
                    lightDemo1.setMname(getMname(host));
                    lightDemo1.setPlace(place);
                    lightDemos.add(lightDemo1);
                    statusList1 = monitor4Dao.getStatusOfPlace(lightDemo1.getMname(), place,
                            lightDemo1.getGroupId());
                    map1 = getExceptionAndDiff(statusList1);
                    placeStatus = new LightDemo();
                    placeStatus.setMname(lightDemo1.getMname());
                    placeStatus.setPlace(place);
                    placeStatus.setOther("开");
                    if (status.equals("1")) {
                        if (getSwitchStatus2(statusList1) == 0) {
                            placeStatus.setOther("关");
                        }
                    }
//                        exceptionByGroupIdOrPlace = webCmdDao.getExceptionByGroupId(lightDemo1.getGroupId(),
// lightDemo1.getMname(), lightDemo1.getPlace());
                } else {
                    //区域
                    for (CommandLight webCmd : webCmds) {
                        LightDemo lightDemo1 = new LightDemo();
                        lightDemo1.setGroupId(Integer.parseInt(webCmd.getCid(), 16));
                        lightDemo1.setStatus(status);
                        lightDemo1.setOther("group");
                        lightDemo1.setMname(getMname(webCmd.getHost()));
                        lightDemo1.setPlace(place);
                        lightDemos.add(lightDemo1);
                    }
                    map1.put("exception", false);
                    map1.put("difference", false);
                    placeStatus = new LightDemo();
                    placeStatus.setMname(getMname(host));
                    placeStatus.setPlace(place);
                    placeStatus.setOther("开");
                    if (status.equals("1")) {
                        placeStatus.setOther("关");
                    }
//                        exceptionByGroupIdOrPlace = webCmdDao.getExceptionByPlace(getMname(host), place);

                }
                List<Integer> statusList2 = monitor4Dao.getStatusOfFloor(getMname(host), place, Integer.parseInt
                        (webCmds.get(0).getCid(), 16));
                centerLNums = getLeftCenter(lightState, mnames, centerLNumList, exception, null, lightDemos);
                Map map2 = getExceptionAndDiff(statusList2);
                LightDemo floorStatus = new LightDemo();
                floorStatus.setMname(getMname(host));
                floorStatus.setOther("开");
                if (status.equals("1")) {
                    if (getSwitchStatus2(statusList2) == 0) {
                        floorStatus.setOther("关");
                    }
//                        floorStatus.setOther("关");
                }
                map.put("centerLNumList", centerLNums);
                map.put("placeStatus", placeStatus);
                map.put("floorStatus", floorStatus);
                map.put("placeException", map1.get("exception"));
                map.put("placeDifference", map1.get("difference"));
                map.put("floorException", map2.get("exception"));
                map.put("floorDifference", map2.get("difference"));
            }

            map.put("lightDemo", lightDemos);
            map.put("scenes", scenes);
//            }
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
                if (commandInfo.getCid()!=null){
                    int groupId = Integer.parseInt(commandInfo.getCid(), 16);
//                if ("0A".equals(commandInfo.getCid())) {
//                    groupId = 10;
//                } else {
//                    groupId = Integer.valueOf(commandInfo.getCid());
//                }
                    if ("37".equals(commandInfo.getY())) {
                        status = "0";
                    } else {
                        status = "1";
                    }
                    lightState = monitor4Dao.getMonitorFromPhoneByGroup(groupId, status, "exhibition");
                }

            }
            map.put("lightState", lightState);
        }
        return map;
    }


    @RequestMapping(value = "/getMonitor2LightStatus", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMonitor2LightStatus() {
        List<LightDemo> lightState2 = new ArrayList<>();
        CommandLight commandInfo = monitor4Dao.getCommandInfo("192.168.10.253");
        List<LightDemo> placeLNumList = monitor2Dao.getPlaceLNum("Office");
        Map<String, Object> map = new HashMap<>();
        String scenes = null;
        if (commandInfo != null) {
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
                } else if ("C1".equals(commandInfo.getCtype()) || "CW".equals(commandInfo.getCtype())) {
                    //pad or 手机 组控
//                    int groupId;
                    if (commandInfo.getCid()!=null){
                        int groupId = Integer.parseInt(commandInfo.getCid(), 16);
//                    if ("0A".equals(commandInfo.getCid())) {
//                        groupId = 10;
//                    } else {
//                        groupId = Integer.valueOf(commandInfo.getCid());
//                    }

                        if ("32".equals(commandInfo.getY())) {
                            status = "1";
                        } else {
                            status = "0";
                        }
                        lightState2 = monitor4Dao.getMonitorFromPhoneByGroup(groupId, status, "Office");
                    }

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
                if (commandInfo.getCid()!=null){
                    int groupId = Integer.parseInt(commandInfo.getCid(), 16);
//                String status;
                    if ("37".equals(commandInfo.getY())) {
                        status = "0";
                    } else {
                        status = "1";
                    }
                    lightState = monitorDao.getMonitorFromPhoneByGroup(groupId, status);
                }

            }
            map.put("lightState", lightState);
        }
        return map;
    }

    private String getMname(String host) {
        String mname = "";
        switch (host) {
            case "192.168.10.11":
                mname = "1楼";
                break;
            case "192.168.10.12":
                mname = "2楼";
                break;
            case "192.168.10.13":
                mname = "3楼";
                break;
            case "192.168.10.14":
                mname = "4楼";
                break;
            case "192.168.10.15":
                mname = "5楼";
                break;
            case "192.168.10.16":
                mname = "6楼";
                break;
            case "192.168.10.17":
                mname = "7楼";
                break;
            case "192.168.10.18":
                mname = "8楼";
                break;
            case "192.168.10.19":
                mname = "9楼";
                break;
            case "192.168.10.20":
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

    private Map<String, List<LightDemo>> getSwitchStatus(List<LightDemo> lightState) {
        Map<String, List<LightDemo>> map = new HashMap<>();
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
        if (allFlag == 0) {
            LightDemo lightDemo = new LightDemo();
            lightDemo.setOther("关");
            allStatus.add(lightDemo);
        }
        map.put("groupStatus", groupStatus);
        map.put("placeStatus", placeStatus);
        map.put("floorStatus", floorStatus);
        map.put("allStatus", allStatus);
        return map;
    }

    private int getSwitchStatus2(List<Integer> statusList) {
        int placeFlag = 0;
        for (int i = 0; i < statusList.size(); i++) {
            if (statusList.get(i) != null) {
                if (statusList.get(i) == 0) {
                    placeFlag = 1;
                }
            }
        }
        return placeFlag;
    }

    public List<CenterException> getCenterException(List<CenterException> mnames, List<LightDemo> lightDemos,
                                                    List<String>
            exceptions) {
        for (int i = 0; i < mnames.size(); i++) {
            int diff = mnames.get(i).getDiff();
            for (LightDemo lightDemo : lightDemos) {
                if (lightDemo.getMname().equals(mnames.get(i).getMname())) {
                    if ("0".equals(lightDemo.getStatus()) || null == lightDemo.getStatus()) {
                        mnames.get(i).setOn(1);//开
                    } else if ("1".equals(lightDemo.getStatus())) {
                        mnames.get(i).setOff(1);//关
                    }
                }
            }
            for (String except : exceptions) {
                if (except.equals(mnames.get(i).getMname())) {
                    mnames.get(i).setDiff(diff++);
                }
            }
        }
        for (int i = 0; i < mnames.size(); i++) {
            if (mnames.get(i).getOn() == 1 && mnames.get(i).getOff() == 1) {
                mnames.get(i).setException(mnames.get(i).getException() + 1);
            }
        }
        return mnames;
    }
}

