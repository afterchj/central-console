package com.example.blt.entity;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-19 15:34
 **/
public class Mnames {

    private String mname;
    private int exception;
    private int diff;
    private int on;
    private int off;
    private List<Map<String,Object>> plcaeList;

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

    public List<Map<String, Object>> getPlcaeList() {
        return plcaeList;
    }

    public void setPlcaeList(List<Map<String, Object>> plcaeList) {
        this.plcaeList = plcaeList;
    }

    @Override
    public String toString() {
        return "Mnames{" +
                "mname='" + mname + '\'' +
                ", exception=" + exception +
                ", diff=" + diff +
                ", on=" + on +
                ", off=" + off +
                ", plcaeList=" + plcaeList.toString() +
                '}';
    }
}
