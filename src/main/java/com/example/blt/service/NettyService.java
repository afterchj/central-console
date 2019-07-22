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
    public void checkSize() throws Exception {
        Set<String> ipSet = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
        Map map = ConsoleUtil.getLight(ConsoleKeys.LINFO.getValue());
        JSONObject object = new JSONObject();
        if (map != null) {
            Integer osize = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
//        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
//        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
            String host = (String) map.get("ip");
            Set lmacSet = (Set) map.get(ConsoleKeys.lMAC.getValue());
            Set vaddrSet = (Set) map.get(ConsoleKeys.VADDR.getValue());
//        int size = ConsoleUtil.getLightSize("Office");
            if (null != lmacSet) {
                logger.warn("lmacSize=" + lmacSet.size() + ",ips:" + ipSet);
                if (null != vaddrSet) {
                    Integer size = sqlSessionTemplate.selectOne("console.selectIn", map);
//                    Integer size = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
                    logger.warn("size=" + size + ",vaddrSize=" + vaddrSet.size());
                    ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), vaddrSet.size());
                    if (size == osize) {
                        ConsoleUtil.cleanKey(ConsoleKeys.LINFO.getValue(), ConsoleKeys.HOSTS.getValue());
                        Map params = new HashMap();
                        params.put("host", host);
                        params.put("list", vaddrSet);
                        try {
                            ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(params));
                        } catch (NoTopicException e) {
                            sqlSessionTemplate.update("console.saveUpdate", params);
                        }
                        ipSet.remove(host);
                    }
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
        sqlSessionTemplate.delete("console.deleteHost");
        logger.warn("nettyService starting...");
//        ExecuteTask.pingStatus(true, 3);
    }
}