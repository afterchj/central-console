package com.example.blt.entity;

/**
 * Created by nannan.li on 2019/5/23.
 */
public enum IP {

    IP103("192.168.16.103"),IP70("192.168.16.70");

    IP( String value) {
        this.value = value;
    }
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
