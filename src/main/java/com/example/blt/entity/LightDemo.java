package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-14 15:02
 **/
public class LightDemo {

    private String mname;
    private String lmac;
    private String lname;
    private Integer place;
    private Integer PlaceLNum;

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

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    @Override
    public String toString() {
        return "LightDemo{" +
                "mname='" + mname + '\'' +
                ", lmac='" + lmac + '\'' +
                ", lname='" + lname + '\'' +
                ", place=" + place +
                ", PlaceLNum=" + PlaceLNum +
                '}';
    }
}
