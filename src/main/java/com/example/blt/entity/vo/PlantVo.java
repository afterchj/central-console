package com.example.blt.entity.vo;


/**
 * @author hongjian.chen
 * @date 2019/9/2 17:54
 */
public class PlantVo implements Cloneable  {
    private int id;
    private String proName;
    private String itemDetail;
    private String detailName;
    private String startDate;
    private String startTime;
    private String endTime;
    private int itemSet;
    private int itemCount;
    private int lightSet;
    private int days;
    private String meshId;
    private int sceneId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(String itemDetail) {
        this.itemDetail = itemDetail;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getItemSet() {
        return itemSet;
    }

    public void setItemSet(int itemSet) {
        this.itemSet = itemSet;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getLightSet() {
        return lightSet;
    }

    public void setLightSet(int lightSet) {
        this.lightSet = lightSet;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
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

    public PlantVo clone() {
        PlantVo plantVo;
        try {
            plantVo = (PlantVo) super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
        return plantVo;
    }

}
