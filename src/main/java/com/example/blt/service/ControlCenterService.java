package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.ControlCenterDao;
import com.example.blt.entity.MPlace;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.*;
import com.example.blt.entity.dd.Week;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-03 14:14
 **/
@Service
public class ControlCenterService {
    @Resource
    private ControlCenterDao controlCenterDao;

    public List<TimeLine> getTimeLinesByMeshId(String meshId) {
        List<TimeLine> timeLines = controlCenterDao.getTimeLinesByMeshId(meshId);
        for (TimeLine timeLine : timeLines) {
            String dayObj = timeLine.getDayObj();
            JSONObject jsonDayObj = JSONObject.parseObject(dayObj);
            String repetition = timeLine.getRepetition();
            String itemSet = timeLine.getItemSet();
            timeLine.setWeek(getWeeks(jsonDayObj, repetition, itemSet));
        }
        return timeLines;
    }

    public String getWeeks(JSONObject jsonWeek, String repetition, String itemSet) {
        jsonWeek.getInteger(Week.MON.getWeekCN());
        StringJoiner sj = new StringJoiner(",");
        sj.add(itemSet).add(repetition);
        if (jsonWeek.getInteger("mon").equals(1)) {
            sj.add(Week.MON.getWeekCN());
        }
        if (jsonWeek.getInteger("tus").equals(1)) {
            sj.add(Week.TUS.getWeekCN());
        }
        if (jsonWeek.getInteger("wed").equals(1)) {
            sj.add(Week.WED.getWeekCN());
        }
        if (jsonWeek.getInteger("thr").equals(1)) {
            sj.add(Week.THR.getWeekCN());
        }
        if (jsonWeek.getInteger("fri").equals(1)) {
            sj.add(Week.FRR.getWeekCN());
        }
        if (jsonWeek.getInteger("sat").equals(1)) {
            sj.add(Week.SAT.getWeekCN());
        }
        if (jsonWeek.getInteger("sun").equals(1)) {
            sj.add(Week.SUN.getWeekCN());
        }
        return sj.toString();
    }

    public List<TimePoint> getTimePointByTsid(Integer tsid) {
        List<TimePoint> timePoints = controlCenterDao.getTimePointByTsid(tsid);
        StringBuffer sb;
        for (TimePoint timePoint : timePoints) {
            Integer scene_id = timePoint.getScene_id();
            switch (scene_id) {
                case 21:
                    timePoint.setSname("ALL OFF");
                    break;
                case 22:
                    timePoint.setSname("ALL ON");
                    break;
                default:
                    sb = new StringBuffer();
                    timePoint.setSname(sb.append("场景").append(scene_id).toString());
                    break;
            }
        }
        return timePoints;
    }

    public List<ControlMaster> getControlGroups(Integer gid, String meshId) {
        if (StringUtils.isNotBlank(meshId)) {//网络设置组
            if (gid == 0){//设置成空组
                controlCenterDao.deleteMeshGroup(meshId);
            }else {//设置组
                controlCenterDao.selectGroup(gid, meshId);
            }
            controlCenterDao.updateHostInfo(meshId, 0);//取消主控设置
        }
        List<ControlMaster> controlMeshs = controlCenterDao.getControlGroups(gid, meshId);
        if (controlMeshs.size() > 0) {
            controlMeshs.forEach(this::setMeshAndPOEStatus);
        }
        return controlMeshs;
    }


    private ControlMaster setMeshAndPOEStatus(ControlMaster controlMesh) {
        String meshId = controlMesh.getMeshId();
        String flag = controlMesh.getFlag();
        List<Map<String, Object>> POEStates = controlCenterDao.getPOEState(meshId);
        int POEStatesSize = POEStates.size();
        String mState;
        String pState = null;
        Boolean master;
        if (POEStatesSize > 0) {
            master = (Boolean) POEStates.get(0).get("master");
            mState = "网络在线";
            int pOffCount = 0;//单个网路下poe离线个数
            boolean states;//poe状态
            if (POEStatesSize == 1) {
                states = (boolean) POEStates.get(0).get("status");
                if (!states) {
                    mState = "网络离线";
                    pState = "（离线）";
                }
            } else {
                for (Map<String, Object> POEState : POEStates) {
                    states = (boolean) POEState.get("status");
                    if (!flag.equals("03") && !flag.equals("3")) {
                        mState = "网络离线";
                    }
                    if (!states) {
                        pOffCount++;
                    }
                }
                if (pOffCount == POEStatesSize) {//网路下所有poe离线
                    mState = "网络离线";
                    pState = "（离线）";
                } else if (pOffCount>0 && (pOffCount < POEStatesSize)) {//网络下部分poe离线
                    pState = "（存在异常）";
                }
            }
        } else {
            mState = "网络离线";
            master = false;
        }
        controlMesh.setpNum(POEStatesSize);
        controlMesh.setmState(mState);
        controlMesh.setpState(pState);
        controlMesh.setMaster(master);
        return controlMesh;
    }

    public Integer getGname(String gname) {
        return controlCenterDao.getGname(gname);
    }

    public Boolean groupOperation(String gname, String type, Integer id) {
        if (type.equals(Operation.delete.getValue())) {
            controlCenterDao.deleteMeshGroupByGid(id);
            controlCenterDao.deleteGroup(id);
        } else {
            Integer count = controlCenterDao.getGname(gname);
            if (count > 0) {//组名重复
                return false;
            }
            if (type.equals(Operation.create.getValue())) {
                controlCenterDao.createGroup(gname);
            } else if (type.equals(Operation.rename.getValue())) {
                controlCenterDao.renameGroup(gname, id);
            }
        }
        return true;
    }

    public List<GroupList> getGroups() {
        return controlCenterDao.getGroups();
    }

    public Integer getMname(String mname) {
        return controlCenterDao.getMname(mname);
    }

    public void renameMesh(String mname, String meshId) {
        controlCenterDao.renameMesh(mname, meshId);
    }

    public Boolean panelOperations(int id, String pname, String type) {
        if (type.equals(Operation.rename.getValue())) {
            Integer count = controlCenterDao.getPname(pname);
            if (count > 0) {//名称重复
                return false;
            }
            controlCenterDao.renamePname(pname, id);
        } else if (type.equals(Operation.delete.getValue())) {//删除面板
            controlCenterDao.deleteHostMesh(id);//删除poe mesh关系
            controlCenterDao.deleteHost(id);
        }
        return true;
    }

    public void updateMaster(String meshId, int type) {
        controlCenterDao.updateHostInfo(meshId, type);
    }

    public List<MeshList> getMeshs() {
        return controlCenterDao.getMeshs();
    }

    public List<ControlHost> getPanels(String meshId) {
        List<ControlHost> controlHosts = controlCenterDao.getPanels(meshId);
        String productType;
        String otaVersion;
        StringBuffer sb;
        String preOtaVersion;
        String lastOtaVersion;
        Integer preVersion;
        Integer lastVersion;
        //离线poe个数
        long offPOECount = controlHosts.stream().filter(controlHost -> controlHost.getState().equals("离线")).count();
        int count = controlHosts.size();
        if (count > 0) {//设置网络状态
            controlHosts.get(0).setmState("网络在线");
            if (offPOECount == count) {
                controlHosts.get(0).setpState("（离线）");
                controlHosts.get(0).setmState("网络离线");
            } else if (offPOECount > 0 && (offPOECount < count)) {
                controlHosts.get(0).setpState("（存在异常）");
            }
            controlHosts.get(0).setpCount(count);
        }
        for (ControlHost controlHost : controlHosts) {
            productType = controlHost.getProductType();
            otaVersion = controlHost.getOtaVersion();
            if (StringUtils.isNotBlank(productType) && StringUtils.isNotBlank(otaVersion)) {
                sb = new StringBuffer();
                sb.append(productType).append("(");
                preOtaVersion = otaVersion.substring(0, 2);
                lastOtaVersion = otaVersion.substring(2, 4);
                preVersion = Integer.valueOf(preOtaVersion);
                lastVersion = Integer.valueOf(lastOtaVersion);
                sb.append(preVersion).append(".").append(lastVersion).append(")");
                controlHost.setProductType(sb.toString());
            }
        }
        return controlHosts;
    }

    public Boolean meshOperations(String mname, String meshId) {
        Boolean flag = true;
        if (StringUtils.isBlank(mname)) {//删除网络
            controlCenterDao.deleteMeshGroup(meshId);//删除mesh group关系
            controlCenterDao.deleteHostByMeshId(meshId);//删除面板
            controlCenterDao.deleteHostMeshByMeshId(meshId);//删除 mesh 面板关系
            controlCenterDao.deleteMesh(meshId);//删除网络
        } else {//重命名网络
            Integer count = controlCenterDao.getMname(mname);
            if (count > 0) {//名称重复
                flag = false;
            } else {
                controlCenterDao.renameMesh(mname, meshId);
            }
        }
        return flag;
    }

    public void reSet() {
        String gname;
        StringBuffer sb;
        controlCenterDao.reSetTimeLine();
        controlCenterDao.reSetTimePoint();
        controlCenterDao.reSetGroup();
        controlCenterDao.reSetHostInfo();
        controlCenterDao.reSetHostMesh();
        controlCenterDao.reSetMesh();
        controlCenterDao.reSetMeshGroup();
        controlCenterDao.reSetCron();
        for (int i = 1; i < 4; i++) {
            sb = new StringBuffer();
            gname = sb.append("组").append(i).toString();
            controlCenterDao.createGroup(gname);
        }
    }

    public ControlHost getMeshAndPOEStatus(ControlHost controlHost, String meshId) {
        String meshState = controlCenterDao.getMeshState(meshId);
        if (!meshState.equals("03") && !meshState.equals("3")) {
            controlHost.setmState("网络离线");
        }
        return controlHost;
    }

    public List<MPlace> getPlaces() {
        return controlCenterDao.getPlaces();
    }
}
