package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-19 15:23
 **/
public class Place {

    private String mname;
    private int place;
    private int exception;
    private int diff;

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getException() {
        return exception;
    }

    public void setException(int exception) {
        this.exception = exception;
    }

    public int getDiff() {
        return diff;
    }

    public void setDiff(int diff) {
        this.diff = diff;
    }

    @Override
    public String toString() {
        return "Place{" +
                "mname='" + mname + '\'' +
                ", place=" + place +
                ", exception=" + exception +
                ", diff=" + diff +
                '}';
    }
}
