package com.example.blt.task;

import com.example.blt.entity.vo.CronVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
    public Map<String, ScheduledFuture> futures = new ConcurrentHashMap<>();
    private ScheduledFuture future;

    public void configureTasks(CronVo cronVo) {
        logger.warn("任务名称 {} 任务执行时间 {}",cronVo.getCronName(),cronVo.getCron());
        int itemSet = cronVo.getItemSet();
        if (itemSet == 1) {
            future = threadPoolTaskScheduler.schedule(new DetailTask(cronVo), new CronTrigger(cronVo.getCron()));
            futures.put(cronVo.getCronName(), future);
        }
    }
}
