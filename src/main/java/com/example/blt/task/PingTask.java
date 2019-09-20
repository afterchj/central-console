package com.example.blt.task;

import com.example.blt.utils.StringBuildUtils;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class PingTask implements Runnable {

    private String ip;
    private String host;
    private String msg;

    public PingTask(String ip, String host, String msg) {
        this.msg = msg;
        this.host = host;
        this.ip = ip;
    }

    @Override
    public void run() {
        StringBuildUtils.buildLightInfo(ip, host, msg);
    }
}
