package com.example.blt.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.entity.dd.Topics;
import com.example.blt.exception.NoTopicException;
import com.example.blt.netty.ClientMain;
import com.example.blt.utils.ConsoleUtil;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Service
public class NettyService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

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
    public void checkSize() {
        Map params = new HashMap();
        Set<String> ipSet = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        Integer size = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
        JSONObject object = new JSONObject();
//        int size = ConsoleUtil.getLightSize("Office");
        if (null != lmacSet) {
            logger.warn("lmacSize=" + lmacSet.size() + ",ips:" + ipSet);
            if (null != vaddrSet) {
                logger.warn("size=" + size + ",vaddrSize=" + vaddrSet.size());
                if (size == null) {
                    ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), vaddrSet.size());
                } else if (size == vaddrSet.size()) {
                    for (String ip : ipSet) {
                        params.put("host", ip);
                        params.put("list", lmacSet);
                        try {
                            ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(params));
                        } catch (NoTopicException e) {
                            sqlSessionTemplate.update("console.saveUpdate2", params);
                        }
                    }
                    return;
                }
            }
            if (ipSet != null) {
                for (String ip : ipSet) {
                    object.put("host", ip);
                    object.put("command", "7701012766");
                    ClientMain.sendCron(object.toJSONString());
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.warn("nettyService starting...");
//        ExecuteTask.pingStatus(true, 3);
    }
}