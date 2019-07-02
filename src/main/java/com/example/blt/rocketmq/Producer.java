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
        rocketMQTemplate.convertAndSend("blt_local_console_topic", args[0]);
        rocketMQTemplate.convertAndSend("blt_remote_console_topic", args[1]);
    }

    public void pushMsg(String... msg) {
        DefaultMQProducer producer = new DefaultMQProducer("main_group");
        producer.setNamesrvAddr("119.3.49.192:9876");
        try {
            producer.start();
            Message message1 = new Message("blt_local_console_topic", msg[0].getBytes());
            Message message2 = new Message("blt_remote_console_topic", msg[1].getBytes());
            SendResult result1 = producer.send(message1);
            SendResult result2 = producer.send(message2);
            log.info("result1=" + result1.getSendStatus() + ",result2=" + result2.getSendStatus());
        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
