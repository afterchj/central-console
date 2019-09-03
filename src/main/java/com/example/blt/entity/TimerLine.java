package com.example.blt.entity;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class TimerLine {

    private Integer ischoose;
    private String item_desc;
    private Integer item_set;
    private String week;
    private List<TimePointParams> timePointList;
    private JSONObject dayObj;
    private String tname;
    private String repetition;
    private Integer item_tag;
    private String item_title;


    public Integer getIschoose() {
        return ischoose;
    }

    public void setIschoose(Integer ischoose) {
        this.ischoose = ischoose;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public Integer getItem_set() {
        return item_set;
    }

    public void setItem_set(Integer item_set) {
        this.item_set = item_set;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public List<TimePointParams> getTimePointList() {
        return timePointList;
    }

    public void setTimePointList(List<TimePointParams> timePointList) {
        this.timePointList = timePointList;
    }

    public JSONObject getDayObj() {
        return dayObj;
    }

    public void setDayObj(JSONObject dayObj) {
        this.dayObj = dayObj;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public Integer getItem_tag() {
        return item_tag;
    }

    public void setItem_tag(Integer item_tag) {
        this.item_tag = item_tag;
    }

    public String getItem_title() {
        return item_title;
    }

    public void setItem_title(String item_title) {
        this.item_title = item_title;
    }
}
