package com.example.blt.service;

import com.example.blt.entity.ConsoleKeys;
import com.example.blt.netty.ClientMain;
import com.example.blt.task.ExecuteTask;
import com.example.blt.utils.ConsoleUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Service
public class NettyService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ClientMain clientMain = new ClientMain();

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
        Set set = ConsoleUtil.getInfo(ConsoleKeys.lMAC.getValue());
        int size = ConsoleUtil.getLightSize("Office");
        if (null != set && set.size() != size) {
            clientMain.sendCron(8001, "7701012766", false);
        }
        logger.info("checkSize...");
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("nettyService starting...");
        ExecuteTask.pingStatus(true, clientMain);
    }
}