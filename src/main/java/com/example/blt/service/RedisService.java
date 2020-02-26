package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.task.DynamicScheduledTask;
import com.example.blt.utils.BuildCronUtil;
import com.example.blt.utils.CalendarUtil;
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
    static Logger logger = LoggerFactory.getLogger(RedisService.class);

    public void pushMsg(JSONObject msg) {
        redisTemplate.convertAndSend("channel_cron", msg);
    }

    public void pushMsg1(JSONObject msg) {
        redisTemplate.convertAndSend("channel_cron1", msg);
    }

    public void consumeMsg(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        List<CronVo> voList = new ArrayList<>();
        try {
            JSONArray cronArr = jsonObject.getJSONArray("cron");
            JSONArray meshArr = jsonObject.getJSONArray("emptyMeshList");
//            logger.warn("emptyMeshList {}", JSON.toJSONString(meshArr));
            List<CronVo> cronVos = sqlSessionTemplate.selectList("console.getCron", meshArr);
            cancelScheduledTask(cronVos);
            if (cronArr != null) {
                for (int i = 0; i < cronArr.size(); i++) {
                    CronVo cronVo = new CronVo();
                    JSONObject object = cronArr.getJSONObject(i);
                    String meshId = object.getString("meshId");
                    Integer sceneId = object.getInteger("sceneId");
                    Integer item_set = object.getInteger("item_set");
                    Integer repetition = object.getInteger("repetition");
                    Integer minute = object.getInteger("minute");
                    Integer hour = object.getInteger("hour");
                    String week = BuildCronUtil.getWeek(object.getString("week"));
                    cronVo.setMeshId(meshId);
                    cronVo.setSceneId(sceneId);
                    cronVo.setItemSet(item_set);
                    cronVo.setRepetition(repetition);
                    cronVo.setCron(minute, hour, week, null);
                    cronVo.setCommand(BuildCronUtil.getCmd(sceneId));
                    cronVo.setCronName(minute, hour, "");
                    voList.add(cronVo);
//                logger.warn("cronName {}", cronVo.getCronName());
                    dynamicScheduledTask.configureTasks(cronVo);
                }
            }
            sqlSessionTemplate.delete("console.deleteCron", meshArr);
            if (voList.size() > 0) {
                sqlSessionTemplate.insert("console.insertCron", voList);
            }
        } catch (Exception e) {
            logger.error("error {}; voList {}", e.getMessage(), voList);
        }
    }

    public void receiverMessage(String msg) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        String meshId = jsonObject.getString("meshId");
        Integer item_set = jsonObject.getInteger("item_set");
//        logger.warn("jsonObject msg [{}]", jsonObject);
        List<CronVo> voList = new ArrayList<>();
        try {
            JSONArray cronArr = jsonObject.getJSONArray("itemDetail");
            List<CronVo> cronVos = sqlSessionTemplate.selectList("plant.getCron", meshId);
            cancelScheduledTask(cronVos);
            if (cronArr != null) {
                int itemCount = cronArr.size();
                for (int i = 0; i < cronArr.size(); i++) {
                    CronVo cronVo = new CronVo();
                    JSONObject object = cronArr.getJSONObject(i);
                    int on = 22;
                    int days = object.getInteger("days");
                    String startDate = object.getString("startDate");
                    String endDate = CalendarUtil.getNextDate(startDate, days);
                    String startTime = object.getString("startTime");
                    String endTime = object.getString("endTime");
                    List<String> list = BuildCronUtil.getDayAndMonth(startDate, endDate);
//                    logger.warn("size=" + list.size() + ",list=" + JSON.toJSONString(list));
                    String[] array = startTime.split(":");
                    String[] array1 = endTime.split(":");
                    int hour = Integer.parseInt(array[0]);
                    int minute = Integer.parseInt(array[1]);
                    int ehour = Integer.parseInt(array1[0]);
                    int eminute = Integer.parseInt(array1[1]);
                    cronVo.setItemCount(itemCount);
                    cronVo.setMeshId(meshId);
                    cronVo.setMeshId(meshId);
                    cronVo.setItemSet(item_set);
                    for (int k = 0; k < list.size(); k++) {
                        CronVo copyCronVo = cronVo.clone();
                        int off = 21;
                        String dayAndMonth = list.get(k);
                        cronVo.setCron(minute, hour, null, dayAndMonth);
                        cronVo.setCommand(BuildCronUtil.getCmd(on));
                        cronVo.setCronName(minute, hour, dayAndMonth);
                        cronVo.setSceneId(off);
                        copyCronVo.setCron(eminute, ehour, null, dayAndMonth);
                        copyCronVo.setCommand(BuildCronUtil.getCmd(off));
                        copyCronVo.setCronName(eminute, ehour, dayAndMonth);
                        copyCronVo.setSceneId(on);
                        voList.add(copyCronVo);
                        voList.add(cronVo);
                    }
                    dynamicScheduledTask.configureTasks(cronVo);
                }
            }
            logger.warn("size=" + voList.size() + ",voList=" + JSON.toJSONString(voList));
//            sqlSessionTemplate.delete("plant.deleteCron");
            if (voList.size() > 0) {
                sqlSessionTemplate.insert("plant.insertCron", voList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("voList {}" + voList);
        }
    }

    public void cancelScheduledTask(List<CronVo> cronVos) {
        ScheduledFuture future;
        for (CronVo cron : cronVos) {
            future = dynamicScheduledTask.futures.get(cron.getCronName());
            if (future != null) {
                future.cancel(true);
            }
        }
    }

//    public static void main(String[] args) {
//        String msg = "{\"item_set\":1,\"itemDetail\":[{\"sceneId\":22,\"days\":8,\"startTime\":\"6:00\",\"endTime\":\"18:00\",\"startDate\":\"2020-2-1\"},{\"sceneId\":22,\"days\":8,\"startTime\":\"6:00\",\"endTime\":\"18:00\",\"startDate\":\"2020-2-9\"},{\"sceneId\":22,\"days\":10,\"startTime\":\"6:00\",\"endTime\":\"18:00\",\"startDate\":\"2020-2-19\"}],\"meshId\":\"70348331\"}";
//        receiverMessage(msg);
//    }
}