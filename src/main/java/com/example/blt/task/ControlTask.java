package com.example.blt.task;

import com.example.blt.netty.ClientMain;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by after on 2019/5/31.
 */
public class ControlTask implements Callable<String> {

    private ClientMain clientMain;
    private String val;

    public ControlTask(ClientMain clientMain, String val) {
        this.clientMain = clientMain;
        this.val = val;
    }

    @Override
    public String call() {
        String result;
        try {
            clientMain.sendCron(8001,val);
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
