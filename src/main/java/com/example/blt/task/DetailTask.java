package com.example.blt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongjian.chen
 * @date 2019/8/19 11:25
 */

public class DetailTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String var;

    public DetailTask(String var) {
        this.var = var;
    }

    @Override
    public void run() {
        logger.warn("执行任务时间..." + var);
    }
}
