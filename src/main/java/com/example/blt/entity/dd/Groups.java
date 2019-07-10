package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/6/27 9:24
 */
public enum Groups {

    GROUPS01("7701041601373766", "7701041601323266"),
    GROUPS02("7701041602373766", "7701041602323266"),
    GROUPS03("7701041603373766", "7701041603323266"),
    GROUPS04("7701041604373766", "7701041604323266"),
    GROUPS05("7701041605373766", "7701041605323266"),
    GROUPS06("7701041605373766", "7701041605323266"),
    GROUPS07("7701041606373766", "7701041606323266"),
    GROUPS08("7701041608373766", "7701041608323266"),
    GROUPS09("7701041609373766", "7701041609323266"),
    GROUPS10("770104160A373766", "770104160A323266"),
    GROUPSA("77010315373766", "77010315323266");

    private String on;
    private String off;

    Groups(String on, String off) {
        this.on = on;
        this.off = off;
    }

    public String getOn() {
        return on;
    }

    public String getOff() {
        return off;
    }

}
