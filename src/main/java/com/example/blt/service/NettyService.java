package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.exception.NoTopicException;
import com.example.blt.netty.ClientMain;
import com.example.blt.task.DynamicScheduledTask;
import com.example.blt.utils.ConsoleUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Service
public class NettyService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private DynamicScheduledTask dynamicScheduledTask;

    //    @Scheduled(cron = "0/30 * * * * ?")
//    public void cronTest1() {
//      ClientMain.sendCron(8001, "7701011B66", false);
//        try {
//            new Thread().sleep(5000);
//          ClientMain.sendCron(8001, "7701012766", false);
//        } catch (InterruptedException e) {
//            logger.error(e.getMessage());
//        }
//    }
//    @Scheduled(cron = "0/30 * * * * ?")
//    public void cronTest2() {
//        Map map=new HashMap();
//        map.put("result","success");
//        map.put("code","0");
//        WebSocket.sendMessage(JSON.toJSONString(map));
//    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkSize() throws InterruptedException {
        Thread.sleep(10000);
        ValueOperations valueOperations = getOpsForValue();
        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        if (null != lmacSet) {
            Set<String> ipSet = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
            Integer result = (Integer) valueOperations.get(ConsoleKeys.LTIMES.getValue());
            int times = result == null ? 1 : result;
            Integer temp = (Integer) ConsoleUtil.getValue(ConsoleKeys.TSIZE.getValue());
            int total = temp == null ? 0 : temp;
            if (ipSet.size() > 0) {
                valueOperations.set(ConsoleKeys.LTIMES.getValue(), times, 10, TimeUnit.MINUTES);
                logger.warn("lmacSize[{}] ips{}", lmacSet.size(), ipSet);
                if (null != vaddrSet) {
                    Map params = new HashMap();
                    Integer old_size;
                    ConsoleUtil.saveInfo(ConsoleKeys.TSIZE.getValue(), vaddrSet.size());
                    for (String ip : ipSet) {
                        params.put("ip", ip);
                        params.put("list", vaddrSet);
                        old_size = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
                        int osize = old_size == null ? 0 : old_size;
                        Integer size = sqlSessionTemplate.selectOne("console.selectIn", params);
                        ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), size);
                        if (osize == size) {
                            logger.warn("ip[{}] old_ize[{}] current_size[{}]", ip, osize, size);
                            ipSet.remove(ip);
                            updateLight(params);
                        }
                    }
                    ConsoleUtil.saveHost(ConsoleKeys.HOSTS.getValue(), ipSet, 10);
                    logger.warn(" flag[{}] ips{}", total == vaddrSet.size(), ipSet);
                    if (total == vaddrSet.size()) {
                        ConsoleUtil.cleanKey(ConsoleKeys.lMAC.getValue(), ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue(), ConsoleKeys.LTIMES.getValue());
                        for (String ip : ipSet) {
                            params.put("ip", ip);
                            params.put("list", vaddrSet);
                            updateLight(params);
                        }
                        ipSet.clear();
                    }
                }
                for (String ip : ipSet) {
                    JSONObject object = new JSONObject();
                    object.put("host", ip);
                    object.put("command", "7701012766");
                    ClientMain.sendCron(object.toJSONString());
                }
                valueOperations.increment(ConsoleKeys.LTIMES.getValue());
            }
            logger.warn("result [{}]", times);
            if (times >= 3) {
                ConsoleUtil.cleanKey(ConsoleKeys.lMAC.getValue(), ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue(), ConsoleKeys.LTIMES.getValue());
            }
        }
    }

    private void updateLight(Map params) {
        try {
            ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(params));
        } catch (NoTopicException e) {
            sqlSessionTemplate.update("console.saveUpdate", params);
        }
    }

    public ValueOperations getOpsForValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        String corn = "0 50 11 ? * SUN-SAT";
        dynamicScheduledTask.configureTasks("0/10 * * * * ?");
        dynamicScheduledTask.configureTasks("0/30 * * * * ?");
//        new DynamicScheduledTask().setCron("0/10 * * * * ?");
        logger.warn("nettyService starting...");
//        ExecuteTask.pingStatus(true, 3);
    }
}