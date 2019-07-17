package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-17 09:17
 **/
public class CenterException {

    private String mname;
    private int exception;
    private int diff;
    private int on;
    private int off;

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public int getOff() {
        return off;
    }

    public void setOff(int off) {
        this.off = off;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getException() {
        return exception;
    }

    public void setException(int exception) {
        this.exception = exception;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }
}
