package com.example.blt.task;

import com.example.blt.utils.StrUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class PingTask implements Callable<Object> {

    Map map = new HashMap();
    private String ip;
    private String[] msg;

    public PingTask(String ip, String... msg) {
        this.msg = msg;
        this.ip = ip;
    }

    @Override
    public Object call() {
        StrUtil.buildLightInfo(ip, msg);
        return map;
    }
}
