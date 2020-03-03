package com.example.blt.entity.vo;

import org.apache.commons.lang.StringUtils;

/**
 * @author hongjian.chen
 * @date 2019/9/2 17:54
 */
public class CronVo implements Cloneable {
    private String cronName;
    private String command;
    private int repetition;
    private int itemSet;
    private String cron;
    private String meshId;
    private int sceneId;
    private int itemCount;

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getRepetition() {
        return repetition;
    }

    public void setRepetition(int repetition) {
        this.repetition = repetition;
    }

    public int getItemSet() {
        return itemSet;
    }

    public void setItemSet(int itemSet) {
        this.itemSet = itemSet;
    }

    public String getCronName() {
        return cronName;
    }

    public void setCronName(int minute, int hour, String dayAndMonth) {
        this.cronName = String.format("task_%s_%s", minute, hour);
        if (StringUtils.isNotEmpty(dayAndMonth)) {
            this.cronName = String.format("task_%s_%s_%s", minute, hour, dayAndMonth);
        }
    }

    public String getCron() {
        return cron;
    }

    public void setCron(int minute, int hour, String week, String dayAndMonth) {
        StringBuilder stringBuilder = new StringBuilder("0");
        stringBuilder.append(" " + minute);
        stringBuilder.append(" " + hour);
        if (StringUtils.isNotEmpty(week)) {
            stringBuilder.append(" " + "? *");
            stringBuilder.append(" " + week);
        } else {
            stringBuilder.append(" " + dayAndMonth);
            stringBuilder.append(" " + "?");
        }
        this.cron = stringBuilder.toString();
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getMeshId() {
        return meshId;
    }

    public void setMeshId(String meshId) {
        this.meshId = meshId;
    }

    public int getSceneId() {
        return sceneId;
    }

    public void setSceneId(int sceneId) {
        this.sceneId = sceneId;
    }

    public CronVo clone() {
        CronVo cronVo;
        try {
            cronVo = (CronVo) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
        return cronVo;
    }

}
