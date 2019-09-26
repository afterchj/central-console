package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.ControlCenterDao;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.*;
import com.example.blt.entity.dd.Week;
import org.apache.commons.lang3.StringUtils;
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

    public List<ControlMaster> getControlGroups(String gname, String meshId) {
        List<ControlMaster> controlMeshs;
        if (StringUtils.isNotBlank(meshId)) {//选择组
            controlCenterDao.updateMasterGidByMeshId(meshId, gname);
        }
        if (StringUtils.isNotBlank(gname) && StringUtils.isBlank(meshId)) {
            controlMeshs = ("全部".equals(gname)) ? controlCenterDao.getControlGroups() : controlCenterDao
                    .getControlGroupsByGname(gname);
        } else {
            controlMeshs = controlCenterDao.getControlGroups();
        }
        if (controlMeshs.size() > 0) {
            for (ControlMaster controlMesh : controlMeshs) {
                meshId = controlMesh.getMeshId();
                List<Map<String, Object>> meshStates = controlCenterDao.getMeshState(meshId);
                if (meshStates.size() > 0) {
                    String mState = "网络在线";
                    String pState = null;
                    int pOffCount = 0;//单个网路下poe离线个数
                    int meshStatesSize = meshStates.size();
                    for (Map<String, Object> meshState : meshStates) {
                        boolean states = (boolean) meshState.get("status");
                        String flag = (String) meshState.get("flag");
                        if (StringUtils.isNotBlank(flag) && !flag.equals("03") ) {
                            mState = "网络离线";
                        }
                        if (!states) {
                            pOffCount++;
                        }
                    }
                    if (pOffCount == meshStatesSize) {//网路下所有poe离线
                        mState = "网络离线";
                        pState = "（离线）";
                    } else if (pOffCount < meshStatesSize) {//网络下部分poe离线
                        pState = "（存在异常）";
                    }
                    controlMesh.setmState(mState);
                    controlMesh.setpState(pState);
                    controlMesh.setpNum(meshStatesSize);
                }
            }
        }
        return controlMeshs;
    }

    public Integer getGname(String gname) {
        return controlCenterDao.getGname(gname);
    }

    public Boolean groupOperation(String gname, String type, Integer id, String meshId) {
        if (type.equals(Operation.delete.getValue())) {
            controlCenterDao.updateMasterByGid(id);
            controlCenterDao.deleteGroup(id);
        } else if (type.equals(Operation.select.getValue())) {
            controlCenterDao.selectGroup(gname, meshId);
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
        } else if (type.equals(Operation.delete.getValue())) {
            controlCenterDao.deleteHost(id);
        }
        return true;
    }

    public void updateMaster(String meshId, int type) {
//        controlCenterDao.updatetMaster(meshId);
        controlCenterDao.updateHostInfo(meshId, type);
    }

    public List<MeshList> getMeshs() {
        return controlCenterDao.getMeshs();
    }

    public List<ControlHost> getPanels(String meshId) {
        return controlCenterDao.getPanels(meshId);
    }

    public Boolean meshOperations(String mname, String meshId) {
        Boolean flag = true;
        if (StringUtils.isBlank(mname)) {
            //删除网络
            controlCenterDao.deleteMesh(meshId);
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
        controlCenterDao.reSetTimeLine();
        controlCenterDao.reSetTimePoint();
        controlCenterDao.reSetGroup();
        controlCenterDao.reSetHostInfo();
        controlCenterDao.reSetHostMesh();
        controlCenterDao.reSetMesh();
        controlCenterDao.reSetMeshGroup();
        controlCenterDao.reSetCron();
    }
}
