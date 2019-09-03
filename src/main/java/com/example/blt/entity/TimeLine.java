package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-09-03 14:09
 **/
public class TimeLine {

    private Integer id;
    private Integer tid;
    private String tname;
    private String meshId;
    private String repetition;
    private String dayObj;
    private String itemSet;
    private String week;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public String getRepetition() {
        return repetition;
    }

    public void setRepetition(String repetition) {
        this.repetition = repetition;
    }

    public String getDayObj() {
        return dayObj;
    }

    public void setDayObj(String dayObj) {
        this.dayObj = dayObj;
    }

    public String getItemSet() {
        return itemSet;
    }

    public void setItemSet(String itemSet) {
        this.itemSet = itemSet;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
