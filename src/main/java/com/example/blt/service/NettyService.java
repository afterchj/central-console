package com.example.blt.service;

import com.alibaba.fastjson.JSONObject;
import com.example.blt.entity.dd.ConsoleKeys;
import com.example.blt.netty.ClientMain;
import com.example.blt.task.ExecuteTask;
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
    private ClientMain clientMain = new ClientMain();
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

//    @Scheduled(cron = "0/30 * * * * ?")
//    public void cronTest1() {
//        clientMain.sendCron(8001, "7701011B66", false);
//        try {
//            new Thread().sleep(5000);
//            clientMain.sendCron(8001, "7701012766", false);
//        } catch (InterruptedException e) {
//            logger.error(e.getMessage());
//        }
//    }
    //    @Scheduled(cron = "0 0/1 * * * ?")
//    public void cronTest2() {
//        clientMain.sendCron(8001, "7701012766", false);
//    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkSize() {
        Set<Map> lmacSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        Set<Map> vaddrSet = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        JSONObject object = new JSONObject();
        object.put("host", "all");
        object.put("command", "7701012766");
//        int size = ConsoleUtil.getLightSize("Office");
        if (null != lmacSet) {
            if (null != vaddrSet) {
                if (lmacSet.size() == vaddrSet.size()) {
                    logger.warn("size=" + lmacSet.size());
                    Map params = new HashMap();
                    params.put("list", lmacSet);
                    sqlSessionTemplate.update("console.saveUpdate2", params);
                    return;
                }
            }
            clientMain.sendCron(8001, object.toJSONString(), false);
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.warn("nettyService starting...");
        ExecuteTask.pingStatus(true, clientMain);
    }
}