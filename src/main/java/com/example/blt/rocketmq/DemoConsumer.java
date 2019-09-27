package com.example.blt.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * @author hongjian.chen
 * @date 2019/6/25 16:26
 */

@Service
@RocketMQMessageListener(topic = "demo_topic", consumerGroup = "blt_consumer_demo_group")
public class DemoConsumer implements RocketMQListener<String> {

    @Value("${rocketmq.model}")
    private String mode;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String message) {
        try {
            logger.warn("message=" + message);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
