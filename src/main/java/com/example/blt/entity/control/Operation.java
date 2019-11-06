package com.example.blt.entity.control;

/**
 * Created by nannan.li on 2019/9/11.
 */
public enum Operation {

    delete("delete"),select("select"),create("create"),rename("rename");

    private String value;

    Operation( String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
