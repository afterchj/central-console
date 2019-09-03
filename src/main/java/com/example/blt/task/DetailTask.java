package com.example.blt.task;

import com.example.blt.entity.vo.CronVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongjian.chen
 * @date 2019/8/19 11:25
 */

public class DetailTask implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private CronVo cronVo;

    public DetailTask(CronVo cronVo) {
        this.cronVo = cronVo;
    }

    @Override
    public void run() {
        logger.warn("meshId [{}] scene_id [{}]" , cronVo.getMeshId(),cronVo.getSceneId());
    }
}
