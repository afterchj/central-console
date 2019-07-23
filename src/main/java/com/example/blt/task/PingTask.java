package com.example.blt.task;

import com.example.blt.utils.StrUtil;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class PingTask implements Runnable {

    private String ip;
    private String msg;

    public PingTask(String ip, String msg) {
        this.msg = msg;
        this.ip = ip;
    }

    @Override
    public void run() {
        StrUtil.buildLightInfo(ip, msg);
    }
}
