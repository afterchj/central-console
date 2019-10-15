package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.example.blt.dao.TpadOfficeDao;
import com.example.blt.entity.office.OfficePa;
import com.example.blt.entity.office.OfficeWS;
import com.example.blt.entity.office.TypeOperation;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.DimmingUtil;
import com.example.blt.utils.JoinCmdUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:17
 **/
@Service
public class TpadOfficeService {

    static Map<String, String> hexColors;
    static Map<String, String> hexLuminances;
    static Map<String, String> decimalismColors;
    static Map<String, String> decimalismLuminances;

    static {
        hexColors = DimmingUtil.toAddHexList2("colors");
        hexLuminances = DimmingUtil.toAddHexList2("luminances");
        decimalismColors = DimmingUtil.toDecimalism("colors");
        decimalismLuminances = DimmingUtil.toDecimalism("luminances");
    }

    Logger logger = LoggerFactory.getLogger(TpadOfficeService.class);

    @Resource
    private TpadOfficeDao tpadOfficeDao;

    @Resource
    private JoinCmdUtil joinCmdUtil;

    public String getHostId(String meshId) {
        String hostId = "";
        List<Map<String, Object>> hosts = tpadOfficeDao.getHost(meshId);
        if (hosts.size() > 1) {
            for (Map<String, Object> host : hosts) {
                if ((Boolean) host.get("master")) {//主控poe
                    hostId = (String) host.get("hostId");
                }
            }
        }
        if (StringUtils.isBlank(hostId) && hosts.size() > 0) {//只有一个poe or 没有主控poe
            hostId = (String) hosts.get(0).get("hostId");
        }
        return hostId;
    }

    public Map<String, Object> getUnits(Map<String, Object> parameterSetting) {
        String unit = (String) parameterSetting.get("unit");
        Integer sceneCount = (Integer) parameterSetting.get("sceneCount");
        List<Integer> scenes = new ArrayList<>();
        Map<String, Object> map = new ConcurrentHashMap<>();
        for (int i = 1; i <= sceneCount; i++) {
            scenes.add(i);
        }
        map.put("scenes", scenes);
//        map.put("sceneCount", sceneCount);
        List<Map<String, Object>> units = new ArrayList<>();
        TypeOperation type = TypeOperation.getType(unit);
        switch (type) {
            case GROUP:
                units = tpadOfficeDao.getGroups();
                break;
            case PLACE:
                units = tpadOfficeDao.getPlaces();
                break;
            case MESH:
                units = tpadOfficeDao.getMeshs();
                break;
        }
        map.put("units", units);
        map.putAll(parameterSetting);
        return map;
    }

    public Map<String, Object> getParameterSetting(String project) {
        return tpadOfficeDao.getParameterSetting(project);
    }

    public void send(String unit, OfficePa office) throws Exception {
        String operation = office.getOperation();
        String x = office.getX();
        String y = office.getY();
        int[] unitArray = office.getUnitArray();
        TypeOperation opeEnum = TypeOperation.getType(operation);
        Integer sceneId = office.getSceneId();
        switch (opeEnum) {
            case ON_OFF:
                if (sceneId != null){
                    throw new Exception("无效的参数sceneId");
                }
                sendCmd(x, y, unitArray, null, unit);
                break;
            case DIMMING:
                if (sceneId != null){
                    throw new Exception("无效的参数sceneId");
                }
                x = hexColors.get(x);
                y = hexLuminances.get(y);
                if (x == null || y == null) {
                    throw new Exception("未知的冷暖色");
                }
                sendCmd(x, y, unitArray, null, unit);
                break;
            case SCENE:
                if (sceneId == null) {
                    throw new Exception("场景id为空");
                }
                sendCmd(null, null, unitArray, sceneId, unit);
                break;
            default:
                throw new Exception("参数错误");
        }
    }

    private void sendCmd(String x, String y, int[] unitArray, Integer sceneId, String unit) throws Exception {
        String hostId;
        String cmd;
        String meshId = null;
        Integer groupId = null;
        String oldMeshId = null;
        String type = null;
        List<Integer> groupIds = new ArrayList<>();
        if (unitArray == null || unitArray.length == 0) {
            hostId = "all";
            if (sceneId != null) {
                type = "scene";
            } else {
                type = "mesh";
            }
            cmd = joinCmdUtil.joinCmd(type, x, y, null, sceneId);
            send(hostId, cmd);
            logger.warn("hostId:{},cmd:{}", hostId, cmd);
        } else {
            for (int i = 0; i < unitArray.length; i++) {
                int id = unitArray[i];
                TypeOperation typeEnum = TypeOperation.getType(unit);
                switch (typeEnum) {
                    case MESH:
                        meshId = tpadOfficeDao.getMesIdByMid(id);
                        type = "mesh";
                        break;
                    case PLACE:
                        oldMeshId = meshId;
                        meshId = tpadOfficeDao.getMeshIdByPid(id);
                        groupIds = tpadOfficeDao.getGroupIdsByPid(id);
                        break;
                    case GROUP:
                        oldMeshId = meshId;
                        meshId = tpadOfficeDao.getMeshIdByGid(id);
                        groupId = tpadOfficeDao.getEGroupId(id);
                        type = "group";
                        break;
                    default:
                        throw new Exception("参数错误");
                }
                hostId = getHostId(meshId);
                if (StringUtils.isBlank(hostId)) {
                    throw new Exception("host未知");
                }
                if (sceneId != null) {//切换场景操作
                    if (oldMeshId!=null && oldMeshId.equals(meshId)){//排除场景命令重复
                        continue;
                    }
                    type = "scene";
                    cmd = joinCmdUtil.joinCmd(type, x, y, groupId, sceneId);
                    send(hostId, cmd);
                    logger.warn("hostId:{},cmd:{}", hostId, cmd);
                }else {
                    if (groupIds.size()>0){//区域为基础单元操作(不包括切换场景)
                        for (Integer group : groupIds) {
                            groupId = group;
                            type = "group";
                            cmd = joinCmdUtil.joinCmd(type, x, y, groupId, sceneId);
                            send(hostId, cmd);
                            logger.warn("hostId:{},cmd:{}", hostId, cmd);
                        }
                    }else {//其它基础单元操作(不包括切换场景)
                        cmd = joinCmdUtil.joinCmd(type, x, y, groupId, sceneId);
                        send(hostId, cmd);
                        logger.warn("hostId:{},cmd:{}", hostId, cmd);
                    }
                }
            }
        }
    }

    private void send(String hostId, String cmd) {
        Map<String, String> map = new HashMap<>();
        map.put("command", cmd);
        map.put("host", hostId);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
    }

    public Map<String,Integer> analysisWsAndStorageStatus(Map<String, Object> parameterSetting,OfficeWS officeWS){
        String x = officeWS.getX();
        String y = officeWS.getY();
        String ctype = officeWS.getCtype();
        String project = officeWS.getProject();
        String hostId = officeWS.getHost();
        Integer status = officeWS.getStatus();
        Integer cid = officeWS.getCid();
        Integer id = 0;
        Map<String,Integer> statusMap = setStatusMap(officeWS);
        String unit = (String) parameterSetting.get("unit");
        switch (ctype){
            case "C0":
                if ("all".equals(hostId)){
                    if ("32".equals(x) || "37".equals(x)){
                        tpadOfficeDao.updateStatus(project,status);
                    }else {
                        storageDimmingStatus(x,y,project);
                    }
                }else {
                    if ("32".equals(x) || "37".equals(x)){
                        id = storageUnitStatus(statusMap,unit,ctype);
                    }else {
                        storageDimmingStatus(x,y,project);
                    }
                }
                break;
            case "CW":
                id = storageUnitStatus(statusMap,unit,ctype);
                break;
            case "42":
                tpadOfficeDao.updateSceneId(cid,project);
                break;
            case "52":
                break;
        }
        status = statusMap.get("status");
        statusMap = new ConcurrentHashMap<>();
        statusMap.put("id",id);
        statusMap.put("status",status);
        return statusMap;
    }

    private Map<String,Integer> setStatusMap(OfficeWS officeWS){
        Map<String,Integer> statusMap = new ConcurrentHashMap<>();
        Integer cid = officeWS.getCid();
        String hostId = officeWS.getHost();
        Integer status = officeWS.getStatus();
        Integer id = 0;
        Integer mid;
        if (!"all".equals(hostId)){
            mid = tpadOfficeDao.getMidByHostId(hostId);
            statusMap.put("mid",mid);
        }
        if (cid != null){
            statusMap.put("cid",cid);
        }
        if (status == null){
            status = 0;
        }
        statusMap.put("status",status);
        statusMap.put("id",id);
        return statusMap;
    }

    private Integer storageUnitStatus(Map<String,Integer> statusMap,String unit,String ctype){
        Integer id = 0;
        switch (ctype){
            case "C0":
                TypeOperation unitEnum = TypeOperation.getType(unit);
                switch (unitEnum){
                    case GROUP:
                        tpadOfficeDao.updateEGroupStatus(statusMap);
                        id = statusMap.get("id");
                        break;
                    case PLACE:
                        id = tpadOfficeDao.getEPid(statusMap);
                        statusMap.put("pid",id);
                        tpadOfficeDao.updateEPlaceStatus(statusMap);
                        break;
                    case MESH:
                        id = statusMap.get("mid");
                        tpadOfficeDao.updateMeshStatus(statusMap);
                        break;
                }
                break;
            case "CW":
                tpadOfficeDao.updateEGroupStatus(statusMap);
                id = statusMap.get("id");
                break;
        }
        return id;
    }

    private void storageDimmingStatus(String x,String y,String project){
        x = decimalismColors.get(x);
        y = decimalismLuminances.get(y);
        tpadOfficeDao.updateXY(project,x,y);
    }

}