package com.example.blt.service;

import com.example.blt.netty.ClientMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by hongjian.chen on 2019/5/17.
 */
@Service
public class NettyService implements ApplicationListener<ContextRefreshedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ClientMain clientMain = new ClientMain();

//    @Scheduled(cron = "0/30 * * * * ?")
//    public void cronTest1() {
//        clientMain.sendCron(8001,"7701011B66", false);
//    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void cronTest2() {
        clientMain.sendCron(8001,"7701012766", false);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("ApplicationListener starting...");
    }
}