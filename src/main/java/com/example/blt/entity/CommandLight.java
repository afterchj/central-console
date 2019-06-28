package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-28 16:37
 **/
public class CommandLight {

    private String cid;
    private String ctype;
    private String x;
    private String y;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype;
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

    @Override
    public String toString() {
        return "CommandLight{" +
                "cid='" + cid + '\'' +
                ", ctype='" + ctype + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                '}';
    }
}
