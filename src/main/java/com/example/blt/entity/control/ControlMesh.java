package com.example.blt.entity.control;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 11:24
 **/
public class ControlMesh {

    private Integer id;
    private String meshId;
    private String mname;
    private Integer gid;
    private String gname;
    private Integer masterId;
    private String state;//网络状态
    private String pState;//网络下面板总状态
    private Integer pNum;//面板个数
    private List<ControlHost> controlHosts;

    public Integer getpNum() {
        return pNum;
    }

    public void setpNum(Integer pNum) {
        this.pNum = pNum;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getpState() {
        return pState;
    }

    public void setpState(String pState) {
        this.pState = pState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ControlHost> getControlHosts() {
        return controlHosts;
    }

    public void setControlHosts(List<ControlHost> controlHosts) {
        this.controlHosts = controlHosts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public Integer getMasterId() {
        return masterId;
    }

    public void setMasterId(Integer masterId) {
        this.masterId = masterId;
    }

    @Override
    public String toString() {
        return "ControlMesh{" +
                "id=" + id +
                ", meshId='" + meshId + '\'' +
                ", mname='" + mname + '\'' +
                ", gid=" + gid +
                ", gname='" + gname + '\'' +
                ", masterId=" + masterId +
                ", state='" + state + '\'' +
                ", pState='" + pState + '\'' +
                ", pNum=" + pNum +
                ", controlHosts=" + controlHosts +
                '}';
    }
}
