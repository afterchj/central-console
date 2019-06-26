package com.example.blt.rocketmq;

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
@RocketMQMessageListener(topic = "blt_console_topic", consumerGroup = "my-consumer_test-topic-1")
public class Consumer implements RocketMQListener<String> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onMessage(String message) {
        //JSONObject jsonObject =  JSON.parseObject(message);
        //OrderPaidEvent orderPaidEvent = JSONObject.parseObject(message, OrderPaidEvent.class);
        log.info("received message: " + message);
    }
}
