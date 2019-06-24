package com.example.blt.entity;

/**
 * Created by nannan.li on 2019/5/23.
 */
public enum IP {

    IP4("192.168.1.191"),IP5("192.168.1.192"),IP6("192.168.1.193"),IP7("192.168.1.195"),IP8("192.168.1.194"),ON("77010315373766"),OFF("77010315323266"),SCENE1("7701021901"),SCENE2("7701021902"),SCENE3("7701021903"),SCENE4("7701021904"),SCENE5("7701021905"),SCENE6("7701021906"),SCENE7("7701021907"),SCENE8("7701021908"),MSG_SCENE("楼"),MSG_STATE("灯");

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
