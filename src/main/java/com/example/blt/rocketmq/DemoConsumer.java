package com.example.blt.rocketmq;

import com.example.blt.netty.ClientMain;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author hongjian.chen
 * @date 2019/6/25 16:26
 */

@Slf4j
@Service
@RocketMQMessageListener(topic = "demo_topic", consumerGroup = "blt_consumer_demo_group")
public class DemoConsumer implements RocketMQListener<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String message) {
        try {
            logger.warn("message=" + message);
          ClientMain.sendCron(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
