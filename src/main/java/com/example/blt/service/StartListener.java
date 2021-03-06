package com.example.blt.service;

import com.example.blt.entity.vo.CronVo;
import com.example.blt.task.DynamicScheduledTask;
import com.example.blt.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
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
public class StartListener implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private DynamicScheduledTask dynamicScheduledTask;
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    @Scheduled(cron = "0/30 * * * * ?")
    public void checkSize() {
        String mode = PropertiesUtil.getValue("spring.profiles.active");
        if ("test".equals(mode)) return;
        ValueOperations valueOperations = getOpsForValue();
        List<String> list1 = sqlSessionTemplate.selectList("console.getAll");
        List<String> list2 = new ArrayList();
        try {
            for (String host : list1) {
                String str = valueOperations.get(host, 0, -1);
                if (StringUtils.isNotEmpty(str)) {
                    list2.add(host);
                }
            }
            saveHostStatus(list1, list2);
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
//        if (flag) {
//            try {
//                ProducerService.pushMsg(Topics.UPDATE_TOPIC.getTopic(), JSON.toJSONString(params));
//            } catch (Exception e) {
//            }
//        }
        sqlSessionTemplate.update("console.saveUpdate", params);
    }

    public void saveHostStatus(List list1, List list2) {
        try {
            if (list2.size() > 0) {
                sqlSessionTemplate.update("console.flushHostsStatus", list2);
                if (list1.size() != list2.size()) {
                    sqlSessionTemplate.update("console.updateHostsStatus", list2);
                }
            } else {
                sqlSessionTemplate.update("console.saveHostsStatus");
            }
        } catch (Exception e) {
            logger.error("flushHostsError {}", e.getMessage());
        }
    }

    public ValueOperations getOpsForValue() {
        return redisTemplate.opsForValue();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.warn("springBoot started.");
        List<CronVo> cronVos = sqlSessionTemplate.selectList("console.getCron");
        List<CronVo> cronVos1 = sqlSessionTemplate.selectList("plant.getCron");
//        logger.warn("cronVos1.size [{}]", cronVos1.size());
        for (CronVo cronVo : cronVos) {
            if (cronVo.getItemSet() == 1) {
                dynamicScheduledTask.configureTasks(cronVo);
            }
        }
        for (CronVo cronVo : cronVos1) {
            if (cronVo.getItemSet() == 1) {
                dynamicScheduledTask.configureTasks(cronVo);
            }
        }
    }
}