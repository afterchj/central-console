package com.example.blt.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * @author hongjian.chen
 * @date 2019/6/27 11:09
 */

@Entity
@Table(name = "t_command_info")
public class CommandInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String host;
    private String mesh_id;
    private String lmac;
    private String ctype;
    private String cid;
    private String x;
    private String y;
    private String other;
    private Date log_date = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMesh_id() {
        return mesh_id;
    }

    public void setMesh_id(String mesh_id) {
        this.mesh_id = mesh_id;
    }

    public String getLmac() {
        return lmac;
    }

    public void setLmac(String lmac) {
        this.lmac = lmac;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Date getLog_date() {
        return log_date;
    }

    public void setLog_date(Date log_date) {
        this.log_date = log_date;
    }
}
