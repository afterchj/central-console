package com.example.blt.task;

import com.example.blt.netty.ClientMain;

import java.util.concurrent.Callable;

/**
 * Created by after on 2019/5/31.
 */
public class ControlTask implements Callable<String> {

    private String val;

    public ControlTask(String val) {
        this.val = val;
    }

    @Override
    public String call() {
        String result;
        try {
          ClientMain.sendCron(val);
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
