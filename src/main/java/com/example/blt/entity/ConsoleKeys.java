package com.example.blt.entity;

/**
 * Created by nannan.li on 2019/5/23.
 */
public enum ConsoleKeys {

    lMAC("l_lmac"), VADDR("l_vaddr"),;

    ConsoleKeys(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }
}