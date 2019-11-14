package com.example.blt.entity.vo;

/**
 * @author hongjian.chen
 * @date 2019/9/2 17:54
 */
public class CronVo {
    private String cronName;
    private String command;
    private int repetition;
    private int itemSet;
    private String cron;
    private String meshId;
    private int sceneId;


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

    public void setCronName(int minute,int hour) {
        this.cronName = String.format("task_%s_%s",minute,hour);
    }

    public String getCron() {
        return cron;
    }

    public void setCron(int minute, int hour, String week) {
        StringBuilder stringBuilder = new StringBuilder("0");
        stringBuilder.append(" " + minute);
        stringBuilder.append(" " + hour);
        stringBuilder.append(" " + "? *");
        stringBuilder.append(" " + week);
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


}
