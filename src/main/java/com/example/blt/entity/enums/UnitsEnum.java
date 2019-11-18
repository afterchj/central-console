package com.example.blt.entity.enums;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-11-15 16:31
 **/
public enum UnitsEnum {
    group(0,"group"),mesh(1,"mesh"),place(2,"place"),space(3,"space");


    private int key;
    private String value;

    UnitsEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
