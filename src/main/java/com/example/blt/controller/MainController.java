package com.example.blt.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.Monitor4Dao;
import com.example.blt.entity.ProjectData;
import com.example.blt.entity.TimePointParams;
import com.example.blt.entity.TimerList;
import com.example.blt.entity.office.SmallRoutine;
import com.example.blt.entity.vo.ConsoleVo;
import com.example.blt.exception.ConsumerMsgException;
import com.example.blt.service.BLTService;
import com.example.blt.service.RedisService;
import com.example.blt.service.TpadOfficeService;
import com.example.blt.socket.EchoClient;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.JoinCmdUtil;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hongjian.chen on 2019/5/17.
 */

@RestController
public class MainController {

    private static final EchoClient CLIENT_MAIN = new EchoClient();
    private Logger logger = LoggerFactory.getLogger(MainController.class);

    @Resource
    private GuavaCacheManager guavaCacheManager;
    @Resource
    private BLTService blTservice;

    @Autowired
    private RedisService redisService;

    @Resource
    private Monitor4Dao monitor4Dao;

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @Resource
    private TpadOfficeService tpadOfficeService;


    @RequestMapping("/switch")
    public String console(ConsoleVo consoleVo) {
        String info = JSON.toJSONString(consoleVo);
        ControlTask task = new ControlTask(CLIENT_MAIN, info);
        String result = ExecuteTask.sendCmd(task);
        return result;
    }

    @RequestMapping("/test")
    public String ping(ConsoleVo consoleVo) {
        List<String> ips = blTservice.getHosts();
        String host = consoleVo.getHost();
        String info = JSON.toJSONString(consoleVo);
        ControlTask task = new ControlTask(CLIENT_MAIN, info);
        String result = ExecuteTask.sendCmd(task);
        int index = consoleVo.getCommand().indexOf("7701011B");
        if (index != -1) {
            if ("all".equals(host)) {
                ExecuteTask.ping(false, 1, ips.toArray(new String[ips.size()]));
            } else {
                ExecuteTask.ping(false, 1, host);
            }
        }
        return result;
    }

//    @RequestMapping(value = "/sendSocket4", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> sendSocket4(String host, String command) {
//        host = blTservice.getHostId(host);
//        Map<String, String> map = new HashMap<>();
//        String result;
//        if (command.equalsIgnoreCase("ON")) {
//            //开
//            command = "77010315373766";
//        } else if (command.equalsIgnoreCase("OFF")) {
//            //关
//            command = "77010315323266";
//        }
//        map.put("host", host);
//        map.put("command", command);
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        result = ExecuteTask.sendCmd(task);
//        map.put("success", result);
//        return map;
//    }
//
//    @RequestMapping(value = "/sendSocket5", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> sendSocket5(String host, String command) {
//        host = blTservice.getHostId(host);
//        Map<String, String> map = new HashMap<>();
//        String success = "success";
//        if (command.equals("场景一")) {
//            command = "7701021901";
//        } else if (command.equals("场景二")) {
//            command = "7701021902";
//        } else if (command.equals("场景三")) {
//            command = "7701021903";
//        } else if (command.equals("场景四")) {
//            command = "7701021904";
//        } else if (command.equals("场景五")) {
//            command = "7701021905";
//        } else if (command.equals("场景六")) {
//            command = "7701021906";
//        } else if (command.equals("场景七")) {
//            command = "7701021907";
//        } else if (command.equals("场景八")) {
//            command = "7701021908";
//        } else if (command.equals("场景1")) {
//            command = "7701021901";
//        } else if (command.equals("场景2")) {
//            command = "7701021902";
//        } else if (command.equals("场景3")) {
//            command = "7701021903";
//        } else if (command.equals("场景4")) {
//            command = "7701021904";
//        } else if (command.equals("场景5")) {
//            command = "7701021905";
//        }
//        String cmd = host + ":" + command;
//        map.put("command", command);
//        map.put("host", host);
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        ExecuteTask.sendCmd(task);
////        SocketUtil.sendCmd2(host, cmd);
//        map.put("success", success);
//        return map;
//    }
//
//    @RequestMapping(value = "/sendSocket6", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> sendSocket6(String host, String command) {
//        host = blTservice.getHostId(host);
//        Map<String, String> map = new HashMap<>();
//        String success = "success";
//        String cmd1 = host + ":" + command;
////        String code1 = SocketUtil.sendCmd2(host, cmd1);
//        map.put("command", command);
//        map.put("host", host);
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        String code = ExecuteTask.sendCmd(task);
//        if ("fail".equals(code)) {
////            失败
//            success = "error";
//        }
//        map.put("success", success);
//        return map;
//    }

    @RequestMapping(value = "/sendByMeshId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendByMeshId(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        host = monitor4Dao.getHostId(host);
        if (host == null) {
            success = "error";
            logger.error("method:sendByMeshId,not find hostId, project:{},command:{}", host, command);
        }
        map.put("command", command);
        map.put("host", host);
        ControlTask task = new ControlTask(CLIENT_MAIN, JSON.toJSONString(map));
        String code = ExecuteTask.sendCmd(task);
        if ("fail".equals(code)) {
//            失败
            success = "error";
        }
        map.put("success", success);
        logger.warn("sendByMeshId result: {},host: {},command: {}", success, host, command);
        return map;
    }

    @RequestMapping(value = "/sendByProject", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendByProject(String project, Integer groupId, String x, String y, String type, Integer sceneId) {
//        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        Map<String, String> onOffMap;
        String host;
        String command = null;
        String success = "success";
        if ("all".equals(project)) {//全控
            host = "all";
        } else {
            host = monitor4Dao.getHostId(project);
            if (host == null) {
                success = "poe不在线";
                logger.error("method:sendByProject;can not find hostId; project:{}", project);
                map.put("success", success);
                return map;
            }
        }
        try {
            if (StringUtils.isNotBlank(x)) {
                onOffMap = tpadOfficeService.changeXY(x, y);
                x = onOffMap.get("x");
                y = onOffMap.get("y");
            }
            command = JoinCmdUtil.joinNewCmd(type, x, y, groupId, sceneId);
            tpadOfficeService.send(host, command);
        } catch (Exception e) {
            success = e.getMessage();
        }
        map.put("success", success);
//        long end = System.currentTimeMillis();
//        logger.warn("send time:{}",end-start);
        logger.warn("method:sendByMeshId; result: {};host: {};command: {}", success, host, command);
        return map;
    }

    @RequestMapping(value = "/sendByProjectForSmallRoutine", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendByProjectForSmallRoutine(@RequestBody SmallRoutine smallRoutine) {
//        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        Map<String, String> onOffMap;
        String host;
        String command = null;
        String success = "success";
        String project = smallRoutine.getProject();
        Integer groupId = smallRoutine.getGroupId();
        String x = smallRoutine.getX();
        String y = smallRoutine.getY();
        String type = smallRoutine.getType();
        Integer sceneId = smallRoutine.getSceneId();
        if ("all".equals(project)) {//全控
            host = "all";
        } else {
            host = monitor4Dao.getHostId(project);
            if (host == null) {
                success = "poe不在线";
                logger.error("method:sendByProject;can not find hostId; project:{}", project);
                map.put("success", success);
                return map;
            }
        }
        try {
            if (StringUtils.isNotBlank(x)) {
                onOffMap = tpadOfficeService.changeXY(x, y);
                x = onOffMap.get("x");
                y = onOffMap.get("y");
            }
            command = JoinCmdUtil.joinNewCmd(type, x, y, groupId, sceneId);
            tpadOfficeService.send(host, command);
        } catch (Exception e) {
            success = e.getMessage();
        }
        map.put("success", success);
//        long end = System.currentTimeMillis();
//        logger.warn("send time:{}",end-start);
        logger.warn("method:sendByMeshId; result: {};host: {};command: {}", success, host, command);
        return map;
    }

    @RequestMapping(value = "/sendByProject2", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> sendByProjectFor2(String x) {
//        long start = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();
        Map<String, String> onOffMap;
        String host;
        String command = null;
        String success = "success";
        Integer groupId = 0;
        String project = "all";
        String type = "mesh";
        if ("all".equals(project)) {//全控
            host = "all";
        } else {
            host = monitor4Dao.getHostId(project);
            if (host == null) {
                success = "poe不在线";
                logger.error("method:sendByProject;can not find hostId; project:{}", project);
                map.put("success", success);
                return map;
            }
        }
        try {
//            if (StringUtils.isNotBlank(x)) {
//                onOffMap = tpadOfficeService.changeXY(x, y);
//                x = onOffMap.get("x");
//                y = onOffMap.get("y");
//            }
            String y;
            if (x.equals("1")) {
                x = "32";
                y = "32";
            } else {
                x = "37";
                y = "37";
            }
            command = JoinCmdUtil.joinNewCmd(type, x, y, groupId, null);
            tpadOfficeService.send(host, command);
        } catch (Exception e) {
            success = e.getMessage();
        }
        map.put("success", success);
//        long end = System.currentTimeMillis();
//        logger.warn("send time:{}",end-start);
        logger.warn("method:sendByMeshId; result: {};host: {};command: {}", success, host, command);
        return map;
    }


    @RequestMapping(value = "/sendScenseByMeshId", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendScenseByMeshId(String host, String command) {
        Map<String, String> map = new HashMap<>();
        String success = "success";
        host = monitor4Dao.getHostId(host);
        if (host == null) {
            success = "error";
            logger.error("method:sendByMeshId,not find hostId, project:{},command:{}", host, command);
        }
        if (command.equals("场景一")) {
            command = "7701021901";
        } else if (command.equals("场景二")) {
            command = "7701021902";
        } else if (command.equals("场景三")) {
            command = "7701021903";
        } else if (command.equals("场景四")) {
            command = "7701021904";
        } else if (command.equals("场景五")) {
            command = "7701021905";
        } else if (command.equals("场景六")) {
            command = "7701021906";
        } else if (command.equals("场景七")) {
            command = "7701021907";
        } else if (command.equals("场景八")) {
            command = "7701021908";
        } else if (command.equals("场景1")) {
            command = "7701021901";
        } else if (command.equals("场景2")) {
            command = "7701021902";
        } else if (command.equals("场景3")) {
            command = "7701021903";
        } else if (command.equals("场景4")) {
            command = "7701021904";
        } else if (command.equals("场景5")) {
            command = "7701021905";
        }
        map.put("command", command);
        map.put("host", host);
        ControlTask task = new ControlTask(CLIENT_MAIN, JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
        map.put("success", success);
        logger.warn("sendScenseByMeshId result: {},host: {},command: {}", success, host, command);
        return map;
    }

    @RequestMapping(value = "/sendSocket7", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> sendSocket7(@RequestParam("commands") List<String> commands, String host) {
        host = blTservice.getHostId(host);
        Map<String, String> map = new HashMap<>();
        String success = "success";
        for (int i = 0; i < commands.size(); i++) {
//        String code1 = SocketUtil.sendCmd2(host, cmd1);
            map.put("command", commands.get(i));
            map.put("host", host);
            ControlTask task = new ControlTask(CLIENT_MAIN, JSON.toJSONString(map));
            String code = ExecuteTask.sendCmd(task);
            if ("fail".equals(code)) {
//            失败
                success = "error";
                break;
            }
        }
        map.put("success", success);
        return map;
    }
//
//    @Resource
//    private CacheableService cacheableService;
//
//    @RequestMapping(value = "/getMsgByMF", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> getMsgByMF(@RequestBody String params) {
//
//        Map<String, String> map = new HashMap<>();
//        map.put("result", "success");
//        System.out.println(params);
//        JSONObject jsonObject = JSONObject.parseObject(params);
//        String msg = jsonObject.getString("msg");
//        String type = jsonObject.getString("type");
//        Cache cache = guavaCacheManager.getCache("msg");
//        cache.put(type, msg);
//        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("msg").get(type);
//        System.out.println(new Date() + " : " + valueWrapper.get());
//        return map;
//    }
//
//    @RequestMapping(value = "/getMsgByMF2", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> getMsgByMF2(@RequestBody String params) {
//        Map<String, String> map = new HashMap<>();
//        map.put("result", "success");
//        System.out.println(params);
//        JSONObject jsonObject = JSONObject.parseObject(params);
//        String msg = jsonObject.getString("msg");
//        String type = jsonObject.getString("type");
//        cacheableService.setCache(type, msg);
////        System.out.println(new Date()+" : "+msg);
////        Cache cache = guavaCacheManager.getCache("msg");
////        cache.put(type,msg);
//        msg = cacheableService.getCache(type);
//        Cache.ValueWrapper valueWrapper = guavaCacheManager.getCache("msg").get(type);
//        System.out.println(new Date() + " : " + msg);
//        return map;
//    }


    @RequestMapping(value = "/uploadDataFromAlink", method = RequestMethod.POST)
    public Map<String, String> uploadDataFromAlink(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        String params = request.getParameter("params");
//        logger.warn("params********************"+params);
        JSONObject jsonObjectParams = JSONObject.parseObject(params);
        String uid = String.valueOf(jsonObjectParams.get("uid"));
        String time = String.valueOf(jsonObjectParams.get("time"));
        int projectId = (int) jsonObjectParams.get("projectId");
        String project_data = JSON.toJSONString(jsonObjectParams.get("project_data"));
        try {
            List cronList = new ArrayList();
            List<String> emptyMeshList = new ArrayList<>();
            List<ProjectData> projectDataList = JSONArray.parseArray(project_data, ProjectData.class);
            for (int i = 0; i < projectDataList.size(); i++) {
                String meshId = projectDataList.get(i).getMeshId();
                Map<String, Object> map2 = new ConcurrentHashMap<>();
                map2.put("meshId", meshId);
                List<TimerList> timerListList = projectDataList.get(i).getTimerList();
                int countTmesh = monitor4Dao.findTMesh(String.valueOf(map2.get("meshId")));
                if (countTmesh == 0) {
                    monitor4Dao.insertTMesh(String.valueOf(map2.get("meshId")), projectDataList.get(i).getMname());
                } else {
                    monitor4Dao.updateTMesh(String.valueOf(map2.get("meshId")), projectDataList.get(i).getMname());
                }
                int count = monitor4Dao.findTimeLine(map2);
                if (count != 0) {
                    sqlSessionTemplate.delete("console.deleteTimerData", map2);
                }
                if (timerListList.size() != 0) {
                    for (int j = 0; j < timerListList.size(); j++) {
                        Integer tid = timerListList.get(j).getTimerLine().getTid();
                        map2.put("tid", tid);
                        map2.put("ischoose", timerListList.get(j).getTimerLine().getIschoose());
                        map2.put("itemSet", timerListList.get(j).getTimerLine().getItem_set());
                        map2.put("item_desc", timerListList.get(j).getTimerLine().getItem_desc());
                        map2.put("week", timerListList.get(j).getTimerLine().getWeek());
                        map2.put("dayObj", JSON.toJSONString(timerListList.get(j).getTimerLine().getDayObj()));
                        map2.put("tname", timerListList.get(j).getTimerLine().getTname());
                        map2.put("repetition", timerListList.get(j).getTimerLine().getRepetition());
                        map2.put("item_tag", timerListList.get(j).getTimerLine().getItem_tag());
                        monitor4Dao.insertTimeLine(map2);
                        List<TimePointParams> timePointList = timerListList.get(j).getTimerLine().getTimePointList();
                        for (int k = 0; k < timePointList.size(); k++) {
                            if (timePointList.get(k).getDetailvalueList() == null) {
                                Map cron = new HashMap();
                                Integer sceneId = timePointList.get(k).getSence_index();
                                map2.put("hour", timePointList.get(k).getHour());
                                map2.put("minute", timePointList.get(k).getMinute());
                                map2.put("sceneId", sceneId);
                                map2.put("lightStatus", timePointList.get(k).getLight_status());
                                if (timerListList.get(j).getTimerLine().getItem_set() == 1) {
//                                logger.warn("meshId {} sceneId {}", meshId, sceneId);
                                    cron.putAll(map2);
                                    cronList.add(cron);
//                                JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map2));
//                                redisService.pushMsg(jsonObject);
                                }
                                monitor4Dao.insertTimePoint(map2);
                            } else {
                                List<TimePointParams> detailvalueList = timePointList.get(k).getDetailvalueList();
                                for (TimePointParams timePointParams : detailvalueList) {
                                    Map cron = new HashMap();
                                    Integer sceneId = timePointParams.getSence_index();
                                    map2.put("hour", timePointParams.getHour());
                                    map2.put("minute", timePointParams.getMinute());
                                    map2.put("sceneId", sceneId);
                                    map2.put("lightStatus", timePointParams.getLight_status());
                                    if (timerListList.get(j).getTimerLine().getItem_set() == 1) {
//                                    logger.warn("meshId {} sceneId {}", meshId, sceneId);
                                        cron.putAll(map2);
                                        cronList.add(cron);
//                                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(map2));
//                                    redisService.pushMsg(jsonObject);
                                    }
                                    monitor4Dao.insertTimePoint(map2);
                                }
                            }
                        }
                    }
                } else if (timerListList.size() == 0) {
                    emptyMeshList.add(String.valueOf(map2.get("meshId")));
                }
            }
            Map<String, String> cronMap = new HashMap<>();
            if (cronList.size() != 0 || emptyMeshList.size() != 0) {
                if (cronList.size() != 0) {
                    cronMap.put("cron", JSONObject.toJSONString(cronList));
                }
                if (emptyMeshList.size() != 0) {
                    cronMap.put("emptyMeshList", JSONObject.toJSONString(emptyMeshList));
                }
                redisService.pushMsg(JSONObject.parseObject(JSON.toJSONString(cronMap)));
            }
            map.put("result", "000");
        } catch (Exception e) {
            map.put("result", "200");
            logger.warn("error********************" + e);
        }
        return map;
    }


    @RequestMapping(value = "/uploadDataFromPlant", method = RequestMethod.POST)
    public Map<String, String> uploadDataFromPlant(@RequestBody String params) {
//        String params = request.getParameter("params");
//        logger.warn("params [{}]", params);
        Map<String, String> map = new HashMap<>();
        map.put("result", "000");
        map.put("msg", "成功");
        try {
            redisService.receiverMessage(params);
            redisService.savePlantTiming(params);
        } catch (ConsumerMsgException e) {
            map.put("result", "200");
            map.put("msg", "系统错误");
        }
        return map;
    }
//    @RequestMapping(value = "/uploadDataFromAlink2", method = RequestMethod.POST)
//    public Map<String, String> uploadDataFromAlink2(HttpServletRequest request) {
//        Map<String, String> map = new HashMap<>();
//        try {
//            List cronList = new ArrayList();
//            Map<String, Object> map2 = new ConcurrentHashMap<>();
//            map2.put("meshId", "38628386");
//            int countTmesh = monitor4Dao.findTMesh("38628386");
//            if (countTmesh == 0) {
//                monitor4Dao.insertTMesh("38628386", "38628386");
//            }
//            map2.put("tid", 1);
//            int count = monitor4Dao.findTimeLine(map2);
//            if (count != 0) {
//                Map<String, Object> map3 = new ConcurrentHashMap<>();
//                map3.put("meshId", map2.get("meshId"));
//                map3.put("tid", 1);
//                sqlSessionTemplate.delete("console.deleteTimerData", map3);
//            }
//            map2.put("ischoose", 0);
//            map2.put("item_set", 1);
//            map2.put("item_desc", "重复,周日,周一,周二,周三,周四,周五,周六");
//            map2.put("week", "重复,周日,周一,周二,周三,周四,周五,周六");
//            map2.put("dayObj", "{\"tus\": 0,\"loop\": 1,\"sat\": 0,\"wed\": 0,\"fri\": 0,\"mon\": 1,\"sun\": 0,\"thr\": 0}");
//            map2.put("tname", "Timeline_00");
//            map2.put("repetition", "1");
//            map2.put("item_tag", 0);
//            monitor4Dao.insertTimeLine(map2);
//            for (int i = 0; i < 24; i++) {
//                for (int j = 0; j < 60; j++) {
//                    Map cron = new HashMap();
//                    Integer sceneId;
//                    if (j % 2 == 0) {
//                        sceneId = 21;
//                    } else {
//                        sceneId = 22;
//                    }
//                    map2.put("hour", i);
//                    map2.put("minute", j);
//                    map2.put("sceneId", sceneId);
//                    map2.put("lightStatus", 0);
//                    cron.putAll(map2);
//                    cronList.add(cron);
//                    monitor4Dao.insertTimePoint(map2);
//                }
//            }
//            if(cronList.size()!=0) {
//                Map<String, String> cronMap = new HashMap<>();
//                cronMap.put("cron", JSONObject.toJSONString(cronList));
//                redisService.pushMsg(JSONObject.parseObject(JSON.toJSONString(cronMap)));
//            }
//            map.put("result", "000");
//        }catch (Exception e) {
//            map.put("result", "200");
//            logger.warn("error********************" + e);
//        }
//        return map;
//    }


}
