package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.task.DynamicScheduledTask;
import org.mybatis.spring.SqlSessionTemplate;
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

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public void pushMsg(JSONObject msg) {
        redisTemplate.convertAndSend("channel_cron", msg);
    }

    public void consumeMsg(String msg) {
        try {
            JSONObject object = JSONObject.parseObject(msg);
            String meshId = object.getString("meshId");
            int sceneId = object.getInteger("sceneId");
            int item_set = object.getInteger("item_set");
            int repetition = object.getInteger("repetition");
            int minute = object.getInteger("minute");
            int hour = object.getInteger("hour");
            String week = getWeek(object.getString("week"));
            CronVo cronVo = new CronVo();
            cronVo.setMeshId(meshId);
            cronVo.setSceneId(sceneId);
            cronVo.setItemSet(item_set);
            cronVo.setRepetition(repetition);
            cronVo.setCron(minute, hour, week);
            cronVo.setCommand(getCmd(sceneId));
            cronVo.setCronName(meshId, sceneId, minute, hour);
            sqlSessionTemplate.insert("console.insertCron", cronVo);
//            String key = String.format("task_%s_%s", meshId, sceneId);
            ScheduledFuture future = dynamicScheduledTask.futures.get(cronVo.getCronName());
            if (future != null) {
                future.cancel(true);
            }
            dynamicScheduledTask.configureTasks(cronVo);
        } catch (Exception e) {
            logger.error("cron error [{}] ", e.getMessage());
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

    public String getCmd(int sceneId) {
        String command;
        switch (sceneId) {
            case 21:
                command = "770103153232CCCC";
                break;
            case 22:
                command = "770103153737CCCC";
                break;
            default:
                StringBuilder cmd = new StringBuilder("77010219");
                String strHex = Integer.toHexString(sceneId).toUpperCase();
                if (strHex.length() == 1) {
                    cmd.append("0" + sceneId);
                } else {
                    cmd.append(sceneId);
                }
                cmd.append("CCCC");
                command = cmd.toString();
                break;
        }
        return command;
    }
}
