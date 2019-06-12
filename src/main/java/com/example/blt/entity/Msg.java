package com.example.blt.entity;

/**
 * @program: central-console
 * @description:
 * @author: Mr.Ma
 * @create: 2019-06-05 16:16
 **/
public class Msg {
    private static int i;
    static {
        i=3;
        System.out.println(i);
    }

    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void getI(){
        i=4;
        System.out.println(i);
    }

    public static void main(String[] args) {
//        System.out.println(Msg.i);
        Msg msg = new Msg();
        msg.getI();
    }
}
