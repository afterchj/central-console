package com.example.blt.entity.dd;

/**
 * @author hongjian.chen
 * @date 2019/6/27 9:24
 */
public enum Groups {

    GROUPS01("","") ;

    private String on;
    private String off;

    private Groups(String on,String off){
        this.on=on;
        this.off=off;
    }

    public String getOn() {
        return on;
    }

    public void setOn(String on) {
        this.on = on;
    }

    public String getOff() {
        return off;
    }

    public void setOff(String off) {
        this.off = off;
    }
}
