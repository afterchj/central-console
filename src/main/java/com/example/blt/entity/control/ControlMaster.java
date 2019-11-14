package com.example.blt.entity.control;


import org.apache.commons.lang.StringUtils;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 11:24
 **/
public class ControlMaster {

    private Integer id;
    private String meshId;
    private String mname;
    private Integer gid;
    private String gname;
    private boolean master;
    private String mState;//网络状态
    private String pState;//网络下面板总状态
    private Integer pNum;//面板个数
    private String flag;
    private Integer placeId;
    private String pname;

    public Integer getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Integer placeId) {
        this.placeId = placeId;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

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
        if (StringUtils.isNotBlank(pState)){
            this.pState = pState;
        }
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
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

    public boolean isMaster() {
        return master;
    }

    public void setMaster(boolean master) {
        this.master = master;
    }

    @Override
    public String toString() {
        return "ControlMaster{" +
                "id=" + id +
                ", meshId='" + meshId + '\'' +
                ", mname='" + mname + '\'' +
                ", gid=" + gid +
                ", gname='" + gname + '\'' +
                ", master=" + master +
                ", mState='" + mState + '\'' +
                ", pState='" + pState + '\'' +
                ", pNum=" + pNum +
                ", flag='" + flag + '\'' +
                '}';
    }
}
