package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.config.WebSocket;
import com.example.blt.entity.dd.ConsoleKeys;
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
    private WebSocket webSocket = new WebSocket();

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
//        webSocket.sendMessage("success");
//    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkSize() {
        Set lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.VADDR.getValue());
        JSONObject object = new JSONObject();
        object.put("host", "all");
        object.put("command", "7701012766");
//        int size = ConsoleUtil.getLightSize("Office");
        if (null != lmacSet) {
            logger.warn("lmacSize=" + lmacSet.size());
            if (null != vaddrSet) {
                logger.warn("vaddrSize=" + vaddrSet.size());
                if (lmacSet.size() == vaddrSet.size()) {
//                    Map params = new HashMap();
//                    params.put("list", lmacSet);
                    sqlSessionTemplate.update("console.saveUpdate2", lmacSet);
                    return;
                }
            }
            ClientMain.sendCron(object.toJSONString());
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.warn("nettyService starting...");
//        ExecuteTask.pingStatus(true, 3);
    }
}