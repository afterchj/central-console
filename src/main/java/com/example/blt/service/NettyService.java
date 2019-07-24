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
        Thread.sleep(20000);
        Integer temp = (Integer) ConsoleUtil.getValue(ConsoleKeys.TSIZE.getValue());
        Integer total = temp == null ? 0 : temp;
        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        if (null != lmacSet) {
            Set<String> ipSet = ConsoleUtil.getInfo(ConsoleKeys.HOSTS.getValue());
            if (ipSet.size() > 0) {
                logger.warn("lmacSize[{}] ips{}", lmacSet.size(), ipSet);
                if (null != vaddrSet) {
                    ConsoleUtil.saveInfo(ConsoleKeys.TSIZE.getValue(), vaddrSet.size());
                    logger.warn("flag[{}]", total == vaddrSet.size());
                    for (String ip : ipSet) {
                        Integer osize = (Integer) ConsoleUtil.getValue(ConsoleKeys.LSIZE.getValue());
                        Map params = new HashMap();
                        params.put("ip", ip);
                        params.put("list", vaddrSet);
                        Integer size = sqlSessionTemplate.selectOne("console.selectIn", params);
                        ConsoleUtil.saveInfo(ConsoleKeys.LSIZE.getValue(), size);
                        if (osize == size) {
                            logger.warn("old_ize[{}] current_size[{}]", osize, size);
                            ipSet.remove(ip);
                            try {
                                ProducerService.pushMsg(Topics.HOST_TOPIC.getTopic(), JSON.toJSONString(params));
                            } catch (NoTopicException e) {
                                sqlSessionTemplate.update("console.saveUpdate", params);
                            }
                        }
                        ConsoleUtil.saveHost(ConsoleKeys.HOSTS.getValue(), ipSet, 10);
                        JSONObject object = new JSONObject();
                        object.put("host", ip);
                        object.put("command", "7701012766");
                        ClientMain.sendCron(object.toJSONString());
                    }
                    if (total == vaddrSet.size()) {
                        ipSet.clear();
                        ConsoleUtil.cleanKey(ConsoleKeys.lMAC.getValue(), ConsoleKeys.VADDR.getValue(), ConsoleKeys.HOSTS.getValue());
                        ConsoleUtil.saveHost(ConsoleKeys.HOSTS.getValue(), ipSet, 10);
                        logger.warn("checkSize ending...");
                    }
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