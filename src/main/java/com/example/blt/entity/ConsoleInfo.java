package com.example.blt.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by hongjian.chen on 2019/5/31.
 */

@Entity
@Table(name = "t_console_info")
public class ConsoleInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String ip;
    private String cmd;
    private String other;
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

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Date getLog_date() {
        return log_date;
    }

    public void setLog_date(Date log_date) {
        this.log_date = log_date;
    }
}
