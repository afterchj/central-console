package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.example.blt.entity.dd.Topics;
import com.example.blt.entity.vo.CronVo;
import com.example.blt.netty.ServerMain;
import com.example.blt.task.DynamicScheduledTask;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Service
public class NettyService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private DynamicScheduledTask dynamicScheduledTask;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkSize() {
        ValueOperations valueOperations = getOpsForValue();
        List<String> hosts = sqlSessionTemplate.selectList("console.getAll");
        List list = new ArrayList();
        try {
            for (String host : hosts) {
                Object object = valueOperations.get(host);
                if (object != null) {
                    list.add(host);
                }
            }
            saveHostStatus(list, list.size() != hosts.size());
        } catch (Exception e) {
            logger.error("updateHostStatus error {}", e.getMessage());
        }
    }

//    public void pingAB() throws InterruptedException {
//        Thread.sleep(10000);
//        ValueOperations valueOperations = getOpsForValue();
//        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
//        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
//        if (null != lmacSet) {
//            Set<String> ipSet = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
//            Integer result = (Integer) valueOperations.get(ConsoleKeys.LTIMES.getValue());
//            int times = result == null ? 1 : result;
//            Integer temp = (Integer) ConsoleUtil.getValue(ConsoleKeys.TSIZE.getValue());
//            int total = temp == null ? 0 : temp;
//            if (ipSet.size() > 0) {
//                valueOperations.set(ConsoleKeys.LTIMES.getValue(), times, 10, TimeUnit.MINUTES);
//                logger.warn("lmacSize[{}] ips{}", lmacSet.size(), ipSet);
//                if (null != vaddrSet) {
//                    Map params = new HashMap();
//                    Integer old_size;
//                    ConsoleUtil.saveInfo(ConsoleKeys.TSIZE.getValue(), vaddrSet.size());
//                    for (String ip : ipSet) {
//                        params.put("ip", ip);
//                        params.put("list", vaddrSet);
//                        old_size = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
//                        int osize = old_size == null ? 0 : old_size;
//                        Integer size = sqlSessionTemplate.selectOne("console.selectIn", params);
//                        ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), size);
//                        if (osize == size) {
//                            logger.warn("ip[{}] old_ize[{}] current_size[{}]", ip, osize, size);
//                            ipSet.remove(ip);
//                            updateLight(params, false);
//                        }
//                    }
//                    ConsoleUtil.saveHost(ConsoleKeys.HOSTS.getValue(), ipSet, 10);
//                    logger.warn(" flag[{}] ips{}", total == vaddrSet.size(), ipSet);
//                    if (total == vaddrSet.size()) {
//                        ConsoleUtil.cleanKey(ConsoleKeys.lMAC.getValue(), ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue(), ConsoleKeys.LTIMES.getValue());
//                        for (String ip : ipSet) {
//                            params.put("ip", ip);
//                            params.put("list", vaddrSet);
//                            updateLight(params, false);
//                        }
//                        ipSet.clear();
//                    }
//                }
//                for (String ip : ipSet) {
//                    JSONObject object = new JSONObject();
//                    object.put("host", ip);
//                    object.put("command", "7701012766");
//                    ClientMain.sendCron(object.toJSONString());
//                }
//                valueOperations.increment(ConsoleKeys.LTIMES.getValue());
//            }
//            logger.warn("result [{}]", times);
//            if (times >= 3) {
//                ConsoleUtil.cleanKey(ConsoleKeys.lMAC.getValue(), ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue(), ConsoleKeys.LTIMES.getValue());
//            }
//        }
//    }

    private void updateLight(Map params, boolean flag) {
        if (flag) {
            try {
                ProducerService.pushMsg(Topics.UPDATE_TOPIC.getTopic(), JSON.toJSONString(params));
            } catch (Exception e) {
            }
        }
        sqlSessionTemplate.update("console.saveUpdate", params);
    }

    public void saveHostStatus(List list, boolean flag) {
        if (list.size() == 0) {
            sqlSessionTemplate.update("console.saveHostsStatus");
            return;
        } else {
            sqlSessionTemplate.update("console.flushHostsStatus", list);
        }
        if (flag) {
            sqlSessionTemplate.update("console.updateHostsStatus", list);
        }
    }

    public ValueOperations getOpsForValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.warn("nettyService starting...");
        new ServerMain().run(8001);
        List<CronVo> cronVos = sqlSessionTemplate.selectList("console.getCron");
        for (CronVo cronVo : cronVos) {
            dynamicScheduledTask.configureTasks(cronVo);
        }
    }
}