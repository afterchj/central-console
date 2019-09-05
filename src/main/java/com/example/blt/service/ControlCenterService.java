package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.dao.ControlCenterDao;
import com.example.blt.entity.TimeLine;
import com.example.blt.entity.TimePoint;
import com.example.blt.entity.control.ControlHost;
import com.example.blt.entity.control.ControlMesh;
import com.example.blt.entity.control.GroupList;
import com.example.blt.entity.control.MeshList;
import com.example.blt.entity.dd.Week;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
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
        for (TimeLine timeLine:timeLines){
            String dayObj = timeLine.getDayObj();
            JSONObject jsonDayObj = JSONObject.parseObject(dayObj);
            String repetition = timeLine.getRepetition();
            String itemSet = timeLine.getItemSet();
            timeLine.setWeek(getWeeks(jsonDayObj,repetition,itemSet));
        }
        return timeLines;
    }

    public String getWeeks(JSONObject jsonWeek,String repetition,String itemSet){
        jsonWeek.getInteger(Week.MON.getWeekCN());
        StringJoiner sj = new StringJoiner(",");
        sj.add(itemSet).add(repetition);
        if (jsonWeek.getInteger("mon").equals(1)){
            sj.add(Week.MON.getWeekCN());
        }
        if (jsonWeek.getInteger("tus").equals(1)){
            sj.add(Week.TUS.getWeekCN());
        }
        if (jsonWeek.getInteger("wed").equals(1)){
            sj.add(Week.WED.getWeekCN());
        }
        if (jsonWeek.getInteger("thr").equals(1)){
            sj.add(Week.THR.getWeekCN());
        }
        if (jsonWeek.getInteger("fri").equals(1)){
            sj.add(Week.FRR.getWeekCN());
        }
        if (jsonWeek.getInteger("sat").equals(1)){
            sj.add(Week.SAT.getWeekCN());
        }
        if (jsonWeek.getInteger("sun").equals(1)){
            sj.add(Week.SUN.getWeekCN());
        }
        return sj.toString();
    }

    public  List<TimePoint> getTimePointByTsid(Integer tsid) {
        List<TimePoint> timePoints = controlCenterDao.getTimePointByTsid(tsid);
        StringBuffer sb;
        for (TimePoint timePoint:timePoints){
            Integer scene_id = timePoint.getScene_id();
            switch (scene_id){
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

    public List<ControlMesh> getControlGroups(String gname) {
        List<ControlMesh> controlMeshs;
        if (StringUtils.isNotBlank(gname)){
            if ("全部".equals(gname)){//全选组
                controlMeshs = controlCenterDao.getControlGroupsByAllGroup(gname);
            }else {//单选组
                controlMeshs = controlCenterDao.getControlGroupsByGname(gname);
            }
        }else {
            controlMeshs = controlCenterDao.getControlGroups();
        }
        if (controlMeshs.size()>0) {
            Iterator<ControlMesh> controlMeshIterator = controlMeshs.iterator();
            while (controlMeshIterator.hasNext()) {
                ControlMesh controlMesh = controlMeshIterator.next();
                List<ControlHost> controlHosts = controlMesh.getControlHosts();
                Integer pNum = controlHosts.size();//面板数量
                if (pNum > 0) {
                    controlMesh.setpNum(pNum);
                }
                Integer normalNum = 0, abnormalNum = 0;//正常/异常面板数量
                Iterator<ControlHost> controlHostIterator = controlHosts.iterator();
                while (controlHostIterator.hasNext()){
                    ControlHost controlHost = controlHostIterator.next();
                    Boolean status = controlHost.isStatus();
                    if (status) {//在线
                        normalNum = normalNum + 1;
                    } else {
                        abnormalNum = abnormalNum + 1;
                    }
                    controlHostIterator.remove();
                }
                if (pNum == normalNum) {//所有面板正常
                    controlMesh.setState("网络在线");
                } else if (pNum == abnormalNum) {//所有面板异常
                    controlMesh.setpState("（离线）");
                    controlMesh.setState("网络离线");
                } else {//面板既有正常又有离线
                    controlMesh.setpState("（存在异常）");
                    controlMesh.setState("网络在线");
                }
            }
        }
        return controlMeshs;
    }

    public Integer getGname(String gname) {
        return controlCenterDao.getGname(gname);
    }

    public Boolean groupOperation(String gname, String type,Integer id,String meshId) {
        if (type.equals("delete")){
            controlCenterDao.deleteGroup(id);
        }else if (type.equals("select")){
            controlCenterDao.selectGroup(gname,meshId);
        }else {
            Integer count = controlCenterDao.getGname(gname);
            if (count >0){//组名重复
                return false;
            }
            if (type.equals("create")){
                controlCenterDao.createGroup(gname);
            }else if (type.equals("rename")){
                controlCenterDao.renameGroup(gname,id);
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
        controlCenterDao.renameMesh(mname,meshId);
    }

    public Boolean panelOperations(String mac, String pname, String type) {
        if (type.equals("rename")){
            Integer count = controlCenterDao.getPname(pname);
            if (count > 0){//名称重复
                return false;
            }
            controlCenterDao.renamePname(pname,mac);
        }else if (type.equals("delete")){
            controlCenterDao.deleteHost(mac);
        }
        return true;
    }

    public void updateMaster(String meshId,String type) {
//        controlCenterDao.updatetMaster(meshId);
        controlCenterDao.updateHostInfo(meshId,type);
    }

    public List<MeshList> getMeshs() {
        return controlCenterDao.getMeshs();
    }

    public List<ControlHost> getPanels(String meshId) {
        return controlCenterDao.getPanels(meshId);
    }
}
