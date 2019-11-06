package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.example.blt.dao.TpadOfficeDao;
import com.example.blt.entity.office.OfficePa;
import com.example.blt.entity.office.OfficeWS;
import com.example.blt.entity.office.TypeOperation;
import com.example.blt.socket.EchoClient;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.blt.entity.office.TypeOperation.DIMMING;
import static com.example.blt.entity.office.TypeOperation.ON_OFF;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-30 11:17
 **/
@Service
public class TpadOfficeService {
    private static final EchoClient CLIENT_MAIN = new EchoClient();
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

    public String getHostId(String meshId) throws Exception {
        String hostId = "";
        List<Map<String, Object>> hosts = tpadOfficeDao.getHost(meshId);
        if (hosts.size()<=0){
            throw new Exception(new StringBuffer().append("poe不在线, meshId:").append(meshId).toString());
        }else {
            if (hosts.size() == 1){
                hostId = (String) hosts.get(0).get("hostId");
            }else {
                for (Map<String, Object> host : hosts) {
                    if ((Boolean) host.get("master")) {//主控poe
                        hostId = (String) host.get("hostId");
                    }
                }
                if (StringUtils.isBlank(hostId)){
                    hostId = (String) hosts.get(0).get("hostId");
                }
            }
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

    public Map<String, Object> getParameterSetting(String project) throws Exception {
        if (StringUtils.isBlank(project)){
            throw new Exception("project is null");
        }
        return tpadOfficeDao.getParameterSetting(project);
    }

    public void send(String unit, OfficePa office) throws Exception {
        String operation = office.getOperation();
        String x = office.getX();
        String y = office.getY();
        int[] unitArray = office.getUnitArray();
        TypeOperation opeEnum = TypeOperation.getType(operation);
        Integer sceneId = office.getSceneId();
        if (ON_OFF.getKey().equals(operation) || DIMMING.getKey().equals(operation)) {
            if (sceneId != null) {
                throw new Exception("无效的参数sceneId");
            }
        }
        switch (opeEnum) {
            case ON_OFF:
                sendCmd(x, y, unitArray, null, unit);
                break;
            case DIMMING:
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
            cmd = JoinCmdUtil.joinNewCmd(type, x, y, null, sceneId);
            send(hostId, cmd);
            logger.warn("method：sendCmd; result success; hostId:{},cmd:{}", hostId, cmd);
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
                if (sceneId != null) {//切换场景操作
                    if (oldMeshId != null && oldMeshId.equals(meshId)) {//排除场景命令重复
                        continue;
                    }
                    type = "scene";
                    cmd = JoinCmdUtil.joinNewCmd(type, x, y, groupId, sceneId);
                    send(hostId, cmd);
                    logger.warn("method：sendCmd; result success; hostId:{},cmd:{}", hostId, cmd);
                } else {
                    if (groupIds.size() > 0) {//区域为基础单元操作(不包括切换场景)
                        for (Integer group : groupIds) {
                            groupId = group;
                            type = "group";
                            cmd = JoinCmdUtil.joinNewCmd(type, x, y, groupId, sceneId);
                            send(hostId, cmd);
                            logger.warn("method：sendCmd; result success; hostId:{},cmd:{}", hostId, cmd);
                        }
                    } else {//其它基础单元操作(不包括切换场景)
                        cmd = JoinCmdUtil.joinNewCmd(type, x, y, groupId, sceneId);
                        send(hostId, cmd);
                        logger.warn("method：sendCmd; result success; hostId:{},cmd:{}", hostId, cmd);
                    }
                }
            }
        }
    }

    public void send(String hostId, String cmd) throws Exception {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.put("command", cmd);
        map.put("host", hostId);
        ControlTask task = new ControlTask(CLIENT_MAIN,JSON.toJSONString(map));
        String code = ExecuteTask.sendCmd(task);
        if ("fail".equals(code)) {
            StringBuffer sb = new StringBuffer();
            String msg = sb.append("发送失败;")
                    .toString();
            throw new Exception(msg);
        }
    }

    /**
     * 解析websocket，存储参数状态,只需要处理id>0的值
     *
     * @param parameterSetting 配置信息
     * @param officeWS         websocket返回值
     * @return 单元主键id, 开关状态(默认为0)
     */
    public Map<String, Integer> analysisWsAndStorageStatus(Map<String, Object> parameterSetting, OfficeWS officeWS) throws Exception {
        String x = officeWS.getX();
        String y = officeWS.getY();
        String ctype = officeWS.getCtype();
        String project = officeWS.getProject();
        String hostId = officeWS.getHost();
//        Integer status = officeWS.getStatus();
        Integer cid = officeWS.getCid();
        Integer id = 0;
        Map<String, Integer> statusMap = setStatusMap(officeWS);
        String unit = (String) parameterSetting.get("unit");
        switch (ctype) {
            case "C1"://组控
            case "C0"://群控
                if (!"32".equals(x) || !"37".equals(x)) {//调光
                    storageDimmingStatus(x, y, project);
                }
                id = storageUnitStatus(statusMap, unit, hostId);
                break;
            case "42"://场景切换
                tpadOfficeDao.updateSceneId(cid, project);
                id = storageUnitStatus(statusMap, unit, hostId);
                break;
            case "52"://遥控器
                break;
        }
        Integer status = statusMap.get("status");
        statusMap = new ConcurrentHashMap<>();
        statusMap.put("id", id);
        statusMap.put("status", status);
        return statusMap;
    }

    /**
     * 存储对应单元状态
     *
     * @param statusMap 单元状态
     * @param unit      何种单元
     * @param hostId    hostId
     * @return 返回单元主键id
     */
    private Integer storageUnitStatus(Map<String, Integer> statusMap, String unit, String hostId) {
        Integer id = 0;
        TypeOperation unitEnum = TypeOperation.getType(unit);
        switch (unitEnum) {//选择何种存储单元
            case GROUP:
                if ("all".equals(hostId)) {
                    tpadOfficeDao.updateAllEGroupStatus(statusMap.get("status"));
                } else {
                    tpadOfficeDao.updateEGroupStatus(statusMap);
                    id = statusMap.get("id");
                }
                break;
            case PLACE:
                if ("all".equals(hostId)) {
                    tpadOfficeDao.updateAllEPlaceStatus(statusMap.get("status"));
                } else {
                    id = tpadOfficeDao.getEPid(statusMap);
                    statusMap.put("pid", id);
                    tpadOfficeDao.updateEPlaceStatus(statusMap);
                }

                break;
            case MESH:
                if ("all".equals(hostId)) {
                    tpadOfficeDao.updateAllMeshStatus(statusMap.get("status"));
                } else {
                    id = statusMap.get("mid");
                    tpadOfficeDao.updateMeshStatus(statusMap);
                }
                break;
        }
        return id;
    }

    /**
     * 存储调光状态
     *
     * @param x       x
     * @param y       y
     * @param project 项目名
     */
    private void storageDimmingStatus(String x, String y, String project) {
        x = decimalismColors.get(x);
        y = decimalismLuminances.get(y);
        tpadOfficeDao.updateXY(project, x, y);
    }

    private Map<String, Integer> setStatusMap(OfficeWS officeWS) throws Exception {
        Map<String, Integer> statusMap = new ConcurrentHashMap<>();
        Integer cid = officeWS.getCid();
        String hostId = officeWS.getHost();
        Integer status = officeWS.getStatus();
        Integer id = 0;
        Integer mid;
        if (!"all".equals(hostId)) {
            mid = tpadOfficeDao.getMidByHostId(hostId);
            if (mid == null) {
                throw new Exception("host未知");
            } else {
                statusMap.put("mid", mid);
            }

        }
        if (cid != null) {
            statusMap.put("cid", cid);
        }
        if (status == null) {
            status = 0;
        }
        statusMap.put("status", status);
        statusMap.put("id", id);
        return statusMap;
    }

    /**
     * 调光时百分比转化为灯中真实的xy值
     *
     * @param x 色温
     * @param y 亮度
     * @return
     */
    public Map<String, String> changeXY(String x, String y) {
        Map<String, String> map = new ConcurrentHashMap<>();
        if (!"32".equals(x) && !"37".equals(x)) {
            x = hexColors.get(x);
            y = hexLuminances.get(y);
        }
        map.put("x", x);
        map.put("y", y);
        return map;
    }
}