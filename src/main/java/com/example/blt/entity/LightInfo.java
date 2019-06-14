package com.example.blt.entity;

import javax.persistence.*;

/**
 * Created by hongjian.chen on 2019/6/14.
 */

@Entity
@Table(name = "t_light")
public class LightInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String lmac;
    private String vaddress;
    private String other;
    private int status;
    private int pid;
    private String pnaem;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "host_id")
    private HostInfo hostInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLmac() {
        return lmac;
    }

    public void setLmac(String lmac) {
        this.lmac = lmac;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPnaem() {
        return pnaem;
    }

    public void setPnaem(String pnaem) {
        this.pnaem = pnaem;
    }

    public HostInfo getHostInfo() {
        return hostInfo;
    }

    public void setHostInfo(HostInfo hostInfo) {
        this.hostInfo = hostInfo;
    }

    public String getVaddress() {
        return vaddress;
    }

    public void setVaddress(String vaddress) {
        this.vaddress = vaddress;
    }
}
