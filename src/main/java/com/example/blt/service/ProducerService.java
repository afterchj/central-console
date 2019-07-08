package com.example.blt.service;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author hongjian.chen
 * @date 2019/7/2 10:42
 */
public class ProducerService {

    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);

    public static void pushMsg(String... msg) {
        DefaultMQProducer producer = new DefaultMQProducer("blt_remote_main_group");
        producer.setInstanceName(UUID.randomUUID().toString());
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            Message message1 = new Message(msg[0], msg[1].getBytes());
            producer.send(message1);
        } catch (Exception e) {
            logger.info(e.getMessage());
        } finally {
            producer.shutdown();
        }
    }
}
