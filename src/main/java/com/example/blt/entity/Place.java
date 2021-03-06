package com.example.blt.entity;

import java.util.List;
import java.util.Map;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-19 15:23
 **/
public class Place {

    private String mname;
    private int place;
    private int exception;
    private int diff;
    private int on;
    private List<Map<String,Object>> groupList;

    public List<Map<String, Object>> getGroupList() {
        return groupList;
    }

    public int getOn() {
        return on;
    }

    public void setOn(int on) {
        this.on = on;
    }

    public void setGroupList(List<Map<String, Object>> groupList) {
        this.groupList = groupList;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
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
        return "Place{" +
                "mname='" + mname + '\'' +
                ", place=" + place +
                ", exception=" + exception +
                ", diff=" + diff +
                ", on=" + on +
                ", groupList=" + groupList +
                '}';
    }
}
