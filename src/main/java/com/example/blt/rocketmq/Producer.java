package com.example.blt.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author hongjian.chen
 * @date 2019/6/25 16:25
 */

@Component
public class Producer {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void push(String... args) {
        rocketMQTemplate.convertAndSend(args[0], args[1]);
    }
}
