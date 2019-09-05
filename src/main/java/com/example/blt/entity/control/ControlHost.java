package com.example.blt.entity.control;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-04 11:27
 **/
public class ControlHost {

    private Integer id;
    private String ip;
    private boolean status;
    private boolean isMaster;
    private String meshId;
    private String mac;
    private String pname;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isMaster() {
        return isMaster;
    }

    public void setMaster(boolean master) {
        isMaster = master;
    }

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "ControlHost{" +
                "id=" + id +
                ", ip='" + ip + '\'' +
                ", status=" + status +
                ", isMaster=" + isMaster +
                ", meshId='" + meshId + '\'' +
                ", mac='" + mac + '\'' +
                ", pname='" + pname + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
