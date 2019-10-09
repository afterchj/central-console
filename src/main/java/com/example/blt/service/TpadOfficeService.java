package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.example.blt.dao.TpadOfficeDao;
import com.example.blt.entity.office.OfficePa;
import com.example.blt.entity.office.TypeOperation;
import com.example.blt.task.ControlTask;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.DimmingUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private TpadOfficeDao tpadOfficeDao;

    public List<String> getHostId(String projectName) {
        List<Map<String, Object>> meshs = tpadOfficeDao.getMesh(projectName);
        List<String> hostIds = new ArrayList<>();
        String meshId;
        String hostId;
        List<Map<String, Object>> hosts;
        if (meshs.size() > 0) {
            for (Map<String, Object> mesh : meshs) {
                hostId = "";
                meshId = (String) mesh.get("meshId");
                hosts = tpadOfficeDao.getHost(meshId);
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
                hostIds.add(hostId);
            }
        }

        return hostIds;
    }

    public List<Map<String, Object>> getParameterSetting(String projectName) {
        List<Map<String, Object>> list = tpadOfficeDao.getParameterSetting();
        List<Map<String, Object>> meshs = tpadOfficeDao.getMesh(projectName);
        list.addAll(meshs);
        return list;
    }

    public List<Map<String, Object>> getUnits(String unit) {
        TypeOperation type = TypeOperation.getType(unit);
        List<Map<String, Object>> units = new ArrayList<>();
        switch (type) {
            case GROUP:
                units = tpadOfficeDao.getGroups();
                break;
            case Place:
                units = tpadOfficeDao.getPlaces();
                break;
            case MESH:
                units = tpadOfficeDao.getMeshs();
                break;
        }
        return units;
    }

    public String getUnitName(String project) {
        return tpadOfficeDao.getUnitName(project);
    }

    public void sendCmd(String unit, OfficePa office) {
        String operation = office.getOperation();
        String x = office.getX();
        String y = office.getY();
        int[] unitArray = office.getUnitArray();
        TypeOperation opeEnum = TypeOperation.getType(operation);
        Integer sceneId = office.getSceneId();
        switch (opeEnum) {
            case ON_OFF:
                sendOnOffOrDimming(x, y, unitArray, null, unit);
                break;
            case DIMMING:
                x = colors.get(x);
                y = luminances.get(y);
                sendOnOffOrDimming(x, y, unitArray, null, unit);
                break;
            case SCENE:
                sendOnOffOrDimming(null, null, unitArray, sceneId, unit);
                break;
        }
    }

    private void sendOnOffOrDimming(String x, String y, int[] unitArray, Integer sceneId, String unit) {
        String hostId = null;
        String cmd;
        String meshId;
        String hexGroupId = null;
        Integer groupId;
        if (unitArray.length == 0) {
            hostId = "all";
            if (sceneId != null) {
                unit = "scene";
            } else {
                unit = "mesh";
            }
            cmd = joinCmd(unit, x, y, null, sceneId);
            send(hostId, cmd);
        } else {
            for (int i = 0; i < unitArray.length; i++) {
                int id = unitArray[i];
                if (unit.equals("mesh")) {
                    meshId = tpadOfficeDao.getMesIdByMid(id);
                    hostId = tpadOfficeDao.getHostIdByMeshId(meshId);
                } else if (unit.equals("place")) {
                    meshId = tpadOfficeDao.getMeshIdByPid(id);
                    hostId = tpadOfficeDao.getHostIdByMeshId(meshId);
                    List<Integer> groupIds = tpadOfficeDao.getGroupIdsByPid(id);
                    for (Integer group : groupIds) {
                        hexGroupId = String.format("%02x", group).toUpperCase();
                        unit = "group";
                    }
                } else if (unit.equals("group")) {
                    meshId = tpadOfficeDao.getMeshIdByGid(id);
                    groupId = tpadOfficeDao.getEGroupId(id);
                    hostId = tpadOfficeDao.getHostIdByMeshId(meshId);
                    hexGroupId = String.format("%02x", groupId).toUpperCase();
                }
            }
            if (sceneId != null) {
                unit = "scene";
            }
            cmd = joinCmd(unit, x, y, hexGroupId, sceneId);
            send(hostId, cmd);
        }
    }

    public String joinCmd(String type, String x, String y, String groupId, Integer sceneId) {
        String cmd = null;
        StringBuffer sb;
        switch (type) {
            case "mesh":
                if (StringUtils.isNotBlank(x)) {
                    sb = new StringBuffer();
                    cmd = sb.append(TypeOperation.MESH_ON_OFF_CMD_START).append(x).append(y).append(
                            CMD_END).toString();
                }
                break;
            case "group":
                sb = new StringBuffer();
                cmd = sb.append(TypeOperation.GROUP_ON_OFF_START).append(groupId).append(x).append(y)
                        .append(CMD_END).toString();
                break;
            case "scene":
                sb = new StringBuffer();
                cmd = sb.append(TypeOperation.SCENE_CMD_START).append(sceneId).toString();
                break;
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
