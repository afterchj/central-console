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
        logger.warn("执行时间[{}]", cronVo.getCron());
        future = threadPoolTaskScheduler.schedule(new DetailTask(cronVo), new CronTrigger(cronVo.getCron()));
        futures.put(cronVo.getMeshId(), future);
    }
}
