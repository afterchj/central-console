package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-22 15:55
 **/
public class LightWeb {

    private Integer lname;
    private String status;
    private String y;

    public Integer getLname() {
        return lname;
    }

    public void setLname(Integer lname) {
        this.lname = lname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "LightWeb{" +
                "lname='" + lname + '\'' +
                ", status='" + status + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
