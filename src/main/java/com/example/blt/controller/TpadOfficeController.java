//package com.example.blt.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.example.blt.entity.office.OfficePa;
//import com.example.blt.entity.office.TypeOperation;
//import com.example.blt.service.TpadOfficeService;
//import com.example.blt.task.ControlTask;
//import com.example.blt.task.ExecuteTask;
//import com.example.blt.utils.DimmingUtil;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @program: central-console
// * @description: 天平办公室
// * @author: Mr.Ma
// * @create: 2019-09-30 10:53
// **/
//@Controller
//@RequestMapping("/office")
//public class TpadOfficeController {
//
//    static Map<String, String> colors;
//    static Map<String, String> luminances;
//
//    static {
//        colors = DimmingUtil.toAddHexList2("colors");
//        luminances = DimmingUtil.toAddHexList2("luminances");
//    }
//
//    @Resource
//    private TpadOfficeService tpadOfficeService;
//
//    Logger logger = LoggerFactory.getLogger(TpadOfficeController.class);
//
////    @CrossOrigin
//    @PostMapping("/get")
//    @ResponseBody
//    public Map<String, Object> get(@RequestBody String projectName) {
////    public String get(@RequestBody String projectName) {
//        List<Map<String,Object>> parameters = tpadOfficeService.getParameterSetting(projectName);
//        return setGroupAndScene(parameters);
////        return "succcess";
//    }
//
//    /**
//     * @param office
//     * @return
//     */
//    @CrossOrigin
//    @PostMapping("/sendCmd")
//    @ResponseBody
//    public String sendCmd(@RequestBody OfficePa office) {
//        List<String> hostIds = tpadOfficeService.getHostId(office.getProjectName());
//        if (hostIds.size() == 0){
//            return "error";
//        }
//        String hostId = hostIds.get(0);
//        String type = office.getType();
//        String operation = office.getOperation();
//        String[] arr = office.getArr();
//        String x = office.getX();
//        String y = office.getY();
//        String cmd = null;
//        StringBuffer sb;
//        TypeOperation typeEnum = TypeOperation.getType(type);
//        TypeOperation numEnum = TypeOperation.getType(x);
//        TypeOperation operationEnum = TypeOperation.getType(operation);
//        switch (typeEnum) {
//            case GROUP://组
//                switch (operationEnum) {
//                    case ON_OFF://开关
//                        for (int i = 0; i < arr.length; i++) {
//                            switch (numEnum) {
//                                case ON:
//                                    sb = new StringBuffer();
//                                    sb.append(TypeOperation.GROUP_ON_OFF_START.getKey()).append(arr[i]).append
//                                            (TypeOperation.GROUP_ON_END.getKey());
//                                    cmd = sb.toString();
//                                    send(hostId, cmd);
//                                    break;
//                                case OFF:
//                                    sb = new StringBuffer();
//                                    sb.append(TypeOperation.GROUP_ON_OFF_START.getKey()).append(arr[i]).append
//                                            (TypeOperation.GROUP_OFF_END.getKey());
//                                    cmd = sb.toString();
//                                    send(hostId, cmd);
//                                    break;
//                            }
//                        }
//                        break;
//                    case DIMMING://调光
//                        sb = new StringBuffer();
//                        x = colors.get(x);
//                        y = luminances.get(y);
//                        sb.append(TypeOperation.DIMMING_CMD_START.getKey()).append(x).append(y).append("66");
//                        cmd = sb.toString();
//                        send(hostId, cmd);
//                        break;
//                }
//                break;
//            case SCENE://scene
//                sb = new StringBuffer();
//                sb.append(TypeOperation.SCENE_CMD_START.getKey()).append(arr[0]);
//                cmd = sb.toString();
//                send(hostId, cmd);
//                break;
//            case MESH://mesh
//                switch (numEnum){
//                    case ON:
//                        cmd = TypeOperation.MESH_ON_CMD.getKey();
//                        send(hostId,cmd);
//                        break;
//                    case OFF:
//                        cmd = TypeOperation.MESH_OFF_CMD.getKey();
//                        send(hostId,cmd);
//                        break;
//                }
//                break;
//        }
//        logger.warn("hostId:{},cmd:{}",hostId,cmd);
//        return "success";
//    }
//
//    public void send(String hostId, String cmd) {
//        Map<String, String> map = new HashMap<>();
//        map.put("command", cmd);
//        map.put("host", hostId);
//        ControlTask task = new ControlTask(JSON.toJSONString(map));
//        ExecuteTask.sendCmd(task);
//    }
//
//    public Map<String, Object> setGroupAndScene(List<Map<String,Object>> parameters) {
//        Map<String, Object> map = new ConcurrentHashMap<>();
//        String name;
//        int count;
//        String meshId;
//        List<Map<String,Object>> meshs = new ArrayList<>();
//        for (Map<String,Object> parameter:parameters){
//            if (parameter.get("count")!=null){
//                name = (String) parameter.get("name");
//                count = (int) parameter.get("count");
//                if (count > 0){
//                    map.putAll(setParameter(name,count));
//                }
//            }
//            meshId = (String) parameter.get("meshId");
//            if (StringUtils.isNotBlank(meshId)){
//                meshs.add(parameter);
//            }
//        }
//        map.put("meshs",meshs);
//        return map;
//    }
//
//    public Map<String, Object> setParameter(String name, int count) {
//        Map<String, Object> map = new ConcurrentHashMap<>();
//        List<String> list = new ArrayList<>();
//        for (int i = 1; i <= count; i++) {
//            String hexStr = String.format("%02x", i).toUpperCase();
//            list.add(hexStr);
//        }
//        map.put(String.format("%sCount",name), count);
//        map.put(name, list);
//        return map;
//    }
//
//}
