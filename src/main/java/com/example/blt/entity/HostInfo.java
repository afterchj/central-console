package com.example.blt.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_host_info")
public class HostInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 64)
    private String ip;
    private boolean status=true;
    private String other;
    private Date create_date=new Date();
    private Date log_date=new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Date getLog_date() {
        return log_date;
    }

    public void setLog_date(Date log_date) {
        this.log_date = log_date;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
