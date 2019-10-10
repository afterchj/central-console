package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.example.blt.dao.TpadOfficeDao;
import com.example.blt.entity.office.OfficePa;
import com.example.blt.entity.office.TypeOperation;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.DimmingUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.blt.entity.office.TypeOperation.CMD_END;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:17
 **/
@Service
public class TpadOfficeService {

    static Map<String, String> colors;
    static Map<String, String> luminances;

    static {
        colors = DimmingUtil.toAddHexList2("colors");
        luminances = DimmingUtil.toAddHexList2("luminances");
    }

    Logger logger = LoggerFactory.getLogger(TpadOfficeService.class);
    @Resource
    private TpadOfficeDao tpadOfficeDao;

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

    public List<Map<String, Object>> getUnits(Map<String,Object> parameterSetting) {
        String unit = (String) parameterSetting.get("unit");
        Integer sceneCount = (Integer) parameterSetting.get("sceneCount");
        List<Integer> scenes = new ArrayList<>();
        Map<String,Object> map = new ConcurrentHashMap<>();
        for (int i=1;i<=sceneCount;i++){
            scenes.add(i);
        }
        map.put("scenes",scenes);
        map.put("sceneCount",sceneCount);
        List<Map<String, Object>> units = new ArrayList<>();
        List<Map<String, Object>> parameterSettings = new ArrayList<>();
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
        map.put("units",units);
        parameterSettings.add(map);
        return parameterSettings;
    }

    public Map<String,Object> getParameterSetting(String project) {
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
                sendCmd(x, y, unitArray, null, unit);
                break;
            case DIMMING:
                x = colors.get(x);
                y = luminances.get(y);
                sendCmd(x, y, unitArray, null, unit);
                break;
            case SCENE:
                if (sceneId == null){
                    throw new Exception();
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
        if (unitArray == null || unitArray.length == 0) {
            hostId = "all";
            if (sceneId != null) {
                unit = "scene";
            } else {
                unit = "mesh";
            }
            cmd = joinCmd(unit, x, y, null, sceneId);
            send(hostId, cmd);
            logger.warn("hostId:{},cmd:{}", hostId, cmd);
        } else {
            for (int i = 0; i < unitArray.length; i++) {
                int id = unitArray[i];
                TypeOperation type = TypeOperation.getType(unit);
                switch (type) {
                    case MESH:
                        meshId = tpadOfficeDao.getMesIdByMid(id);
                        break;
                    case PLACE:
                        meshId = tpadOfficeDao.getMeshIdByPid(id);
                        List<Integer> groupIds = tpadOfficeDao.getGroupIdsByPid(id);
                        for (Integer group : groupIds) {
                            groupId = group;
                            unit = "group";
                        }
                        break;
                    case GROUP:
                        meshId = tpadOfficeDao.getMeshIdByGid(id);
                        groupId = tpadOfficeDao.getEGroupId(id);
                        break;
                    default:
                        throw new Exception("参数错误");
                }
                hostId = getHostId(meshId);
                if (StringUtils.isBlank(hostId)) {
                    throw new Exception();
                }
                if (sceneId != null) {
                    unit = "scene";
                }
                cmd = joinCmd(unit, x, y, groupId, sceneId);
                send(hostId, cmd);
                logger.warn("hostId:{},cmd:{}", hostId, cmd);
            }
        }
    }

    public String joinCmd(String type, String x, String y, Integer groupId, Integer sceneId) throws Exception {
        String cmd = null;
        StringBuffer sb;
        TypeOperation type1 = TypeOperation.getType(type);
        switch (type1) {
            case MESH:
                if (StringUtils.isNotBlank(x)) {
                    sb = new StringBuffer();
                    cmd = sb.append(TypeOperation.MESH_ON_OFF_CMD_START.getKey()).append(x).append(y).append(
                            CMD_END.getKey()).toString();
                }
                break;
            case GROUP:
                sb = new StringBuffer();
                String hexGroupId = String.format("%02x", groupId).toUpperCase();
                cmd = sb.append(TypeOperation.GROUP_ON_OFF_START.getKey()).append(hexGroupId).append(x).append(y)
                        .append(CMD_END.getKey()).toString();
                break;
            case SCENE:
                sb = new StringBuffer();
                String HexSceneId = String.format("%02x",sceneId).toUpperCase();
                cmd = sb.append(TypeOperation.SCENE_CMD_START.getKey()).append(HexSceneId).toString();
                break;
            default:
                throw new Exception("参数错误");
        }
        return cmd;
    }


    private void send(String hostId, String cmd) {
        Map<String, String> map = new HashMap<>();
        map.put("command", cmd);
        map.put("host", hostId);
        ControlTask task = new ControlTask(JSON.toJSONString(map));
        ExecuteTask.sendCmd(task);
    }

}
