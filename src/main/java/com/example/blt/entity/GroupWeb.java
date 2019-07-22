package com.example.blt.entity;

import java.util.List;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-22 15:53
 **/
public class GroupWeb {

    private int groupId;
    private int exception;
    private int status;
    private int diff;
    private List<LightWeb> lightList;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getException() {
        return exception;
    }

    public void setException(int exception) {
        this.exception = exception;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    public List<LightWeb> getLightList() {
        return lightList;
    }

    public void setLightList(List<LightWeb> lightList) {
        this.lightList = lightList;
    }

    @Override
    public String toString() {
        return "GroupWeb{" +
                "groupId=" + groupId +
                ", exception=" + exception +
                ", status=" + status +
                ", diff=" + diff +
                ", lightList=" + lightList +
                '}';
    }
}
