package com.example.blt.task;

import com.example.blt.utils.StrUtil;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by hongjian.chen on 2019/6/17.
 */
public class PingTask implements Callable<Object> {

    private String msg;
    private String ip;

    public PingTask(String msg, String ip) {
        this.msg = msg;
        this.ip = ip;
    }

    @Override
    public Object call() throws Exception {
        Map map= StrUtil.buildLightInfo(msg,ip);
        return map.get("result");
    }
}
