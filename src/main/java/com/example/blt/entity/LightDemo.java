package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-14 15:02
 **/
public class LightDemo {

    private String mname;//楼层
    private String lmac;//灯mac
    private Integer lname;//灯序号
    private Integer place;//区域
    private Integer PlaceLNum;//区域中灯个数
    private String status;
    private Integer groupId;
    private String y;
    private String other;
    private int count;
    private int onNum;
    private int offNum;

    public int getOnNum() {
        return onNum;
    }

    public void setOnNum(int onNum) {
        this.onNum = onNum;
    }

    public int getOffNum() {
        return offNum;
    }

    public void setOffNum(int offNum) {
        this.offNum = offNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPlaceLNum() {
        return PlaceLNum;
    }

    public void setPlaceLNum(Integer placeLNum) {
        PlaceLNum = placeLNum;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public String getLmac() {
        return lmac;
    }

    public void setLmac(String lmac) {
        this.lmac = lmac;
    }

    public Integer getLname() {
        return lname;
    }

    public void setLname(Integer lname) {
        this.lname = lname;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    @Override
    public String toString() {
        return "LightDemo{" +
                "mname='" + mname + '\'' +
                ", lmac='" + lmac + '\'' +
                ", lname='" + lname + '\'' +
                ", Place=" + place +
                ", PlaceLNum=" + PlaceLNum +
                ", status='" + status + '\'' +
                ", groupId=" + groupId +
                ", y=" + y +
                ", other=" + other +
                '}';
    }
}
