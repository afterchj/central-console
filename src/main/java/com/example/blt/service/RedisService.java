package com.example.blt.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.task.DynamicScheduledTask;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
        JSONObject jsonObject = JSONObject.parseObject(msg);
        List<CronVo> voList = new ArrayList<>();
        try {
            List<CronVo> cronVos = sqlSessionTemplate.selectList("console.getCron");
            ScheduledFuture future;
            for (CronVo cron : cronVos) {
                future = dynamicScheduledTask.futures.get(cron.getCronName());
                if (future != null) {
                    future.cancel(true);
                }
            }
            JSONArray cronArr = jsonObject.getJSONArray("cron");
            for (int i = 0; i < cronArr.size(); i++) {
                CronVo cronVo = new CronVo();
                JSONObject object = cronArr.getJSONObject(i);
                String meshId = object.getString("meshId");
                Integer sceneId = object.getInteger("sceneId");
                Integer item_set = object.getInteger("item_set");
                Integer repetition = object.getInteger("repetition");
                Integer minute = object.getInteger("minute");
                Integer hour = object.getInteger("hour");
                String week = getWeek(object.getString("week"));
                cronVo.setMeshId(meshId);
                cronVo.setSceneId(sceneId);
                cronVo.setItemSet(item_set);
                cronVo.setRepetition(repetition);
                cronVo.setCron(minute, hour, week);
                cronVo.setCommand(getCmd(sceneId));
                cronVo.setCronName(meshId, sceneId, minute, hour);
                voList.add(cronVo);
//                logger.warn("cronName {}", cronVo.getCronName());
                dynamicScheduledTask.configureTasks(cronVo);
            }
            sqlSessionTemplate.insert("console.insertCron", voList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWeek(String str) {
        if (StringUtils.isEmpty(str)) return null;
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
