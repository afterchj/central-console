package com.example.blt.task;

import com.example.blt.socket.EchoClient;

import java.util.concurrent.Callable;

/**
 * Created by after on 2019/5/31.
 */
public class ControlTask implements Callable<String> {

    private String val;
    private EchoClient clientMain;

    public ControlTask(EchoClient clientMain, String val) {
        this.clientMain = clientMain;
        this.val = val;
    }

    @Override
    public String call() {
        String result;
        try {
            clientMain.sendCron(val);
            result = "ok";
        } catch (Exception e) {
            result = "fail";
        }
        return result;
    }

//    public String executeTask() {
//
//    }
}
