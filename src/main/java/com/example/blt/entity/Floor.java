package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-07-18 14:23
 **/
public class Floor {

    private String name;
    private int exceptionNum;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getExceptionNum() {
        return exceptionNum;
    }

    public void setExceptionNum(int exceptionNum) {
        this.exceptionNum = exceptionNum;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
