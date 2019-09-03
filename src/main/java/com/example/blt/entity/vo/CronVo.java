package com.example.blt.entity.vo;

/**
 * @author hongjian.chen
 * @date 2019/9/2 17:54
 */
public class CronVo {
    private String second = "0";
    private String minute;
    private String hour;
    private String week;
    private int repetition;
    private int item_set;
    private String meshId;
    private int scene_index;

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public int getScene_index() {
        return scene_index;
    }

    public void setScene_index(int scene_index) {
        this.scene_index = scene_index;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getItem_set() {
        return item_set;
    }

    public void setItem_set(int item_set) {
        this.item_set = item_set;
    }

    public String getCron() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" " + second);
        stringBuilder.append(" " + minute);
        stringBuilder.append(" " + hour);
        stringBuilder.append(" " + "? *");
        stringBuilder.append(" " + week);
        return stringBuilder.toString();
    }

}
