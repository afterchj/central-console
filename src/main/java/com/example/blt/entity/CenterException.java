package com.example.blt.entity;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-17 09:17
 **/
public class CenterException {

    private String mname;
    private int place;
    private int groupId;
    private int exception;
    private int diff;
    private int on;
    private int off;
    private List<Map<String,Object>> plcaeList;

    public List<Map<String, Object>> getPlcaeList() {
        return plcaeList;
    }

    public void setPlcaeList(List<Map<String, Object>> plcaeList) {
        this.plcaeList = plcaeList;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    @Override
    public String toString() {
        return "CenterException{" +
                "mname='" + mname + '\'' +
                ", Place=" + place +
                ", groupId=" + groupId +
                ", exception=" + exception +
                ", diff=" + diff +
                ", on=" + on +
                ", off=" + off +
                ", plcaeList=" + plcaeList.toString() +
                '}';
    }
}
