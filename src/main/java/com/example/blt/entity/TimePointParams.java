package com.example.blt.entity;

import java.util.List;

public class TimePointParams {

    private Integer hour;
    private Integer light_status;
    private Integer time;
    private Integer minute;
    private Integer sence_index;
    private List<TimePointParams> detailvalueList;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getLight_status() {
        return light_status;
    }

    public void setLight_status(Integer light_status) {
        this.light_status = light_status;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSence_index() {
        return sence_index;
    }

    public void setSence_index(Integer sence_index) {
        this.sence_index = sence_index;
    }

    public List<TimePointParams> getDetailvalueList() {
        return detailvalueList;
    }

    public void setDetailvalueList(List<TimePointParams> detailvalueList) {
        this.detailvalueList = detailvalueList;
    }
}
