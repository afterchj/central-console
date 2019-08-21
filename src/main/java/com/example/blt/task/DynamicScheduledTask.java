package com.example.blt.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledFuture;

/**
 * @author hongjian.chen
 * @date 2019/8/19 10:21
 */

@Component
public class DynamicScheduledTask {

    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ScheduledFuture<?> future;
    //时间表达式  每2秒执行一次
//    private String cron = "0 50 11 ? * SUN-SAT";

    public void configureTasks(String cron) {
        future = threadPoolTaskScheduler.schedule(new DetailTask(cron), new CronTrigger(cron));
    }
}
