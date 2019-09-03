package com.example.blt.entity;

import java.util.List;

/**
 * Created by chenhao.lu on 2019/9/2.
 */
public class ProjectData {

    private String meshId;
    private String mname;
    private List<TimerList> timerList;

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public List<TimerList> getTimerList() {
        return timerList;
    }

    public void setTimerList(List<TimerList> timerList) {
        this.timerList = timerList;
    }
}
