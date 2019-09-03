package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.task.DynamicScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledFuture;

/**
 * @author hongjian.chen
 * @date 2019/9/2 13:39
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private DynamicScheduledTask dynamicScheduledTask;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void pushMsg(JSONObject msg) {
        redisTemplate.convertAndSend("channel_cron", msg);
    }

    public void consumeMsg(String msg) {
        try {
            JSONObject object = JSONObject.parseObject(msg);
            logger.warn("收到消息{}", object);
            int item_set = object.getInteger("item_set");
            CronVo cronVo = new CronVo();
            cronVo.setMinute(object.getString("minute"));
            cronVo.setHour(object.getString("hour"));
            cronVo.setSceneId(object.getInteger("sceneId"));
            cronVo.setWeek(getWeek(object.getString("week")));
            cronVo.setMeshId(object.getString("meshId"));
            cronVo.setItem_set(item_set);
            boolean flag = item_set == 0;
            logger.warn("flag [{}]", flag);
            if (flag) {
                ScheduledFuture future = dynamicScheduledTask.futures.get(object.getString("meshId"));
                if (future != null) {
                    future.cancel(true);
                    logger.warn("isCancel [{}]" + future.isCancelled());
                }
            } else {
                dynamicScheduledTask.configureTasks(cronVo);
            }
        } catch (Exception e) {
            logger.error("cron error [] ", e);
        }
    }

    public String getWeek(String str) {
        String[] weeks = str.split(",");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 1; i < weeks.length; i++) {
            switch (weeks[i]) {
                case "周日":
                    weeks[i] = "SUN";
                    break;
                case "周一":
                    weeks[i] = "MON";
                    break;
                case "周二":
                    weeks[i] = "TUE";
                    break;
                case "周三":
                    weeks[i] = "WED";
                    break;
                case "周四":
                    weeks[i] = "THU";
                    break;
                case "周五":
                    weeks[i] = "FRI";
                    break;
                case "周六":
                    weeks[i] = "SAT";
                    break;
            }
            if (i == weeks.length - 1) {
                stringBuilder.append(weeks[i]);
            } else {
                stringBuilder.append(weeks[i] + ",");
            }
        }
        return stringBuilder.toString();
    }
}
