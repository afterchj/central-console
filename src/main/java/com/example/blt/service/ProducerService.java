package com.example.blt.service;

import com.example.blt.exception.NoTopicException;
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

    public static void pushMsg(String... msg) throws NoTopicException {
        DefaultMQProducer producer = new DefaultMQProducer("blt_local_main_group");
        producer.setInstanceName(UUID.randomUUID().toString());
        producer.setNamesrvAddr("119.3.49.192:9876");
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            Message message = new Message(msg[0], msg[1].getBytes());
            producer.send(message);
        } catch (Exception e) {
            throw new NoTopicException("Topic Not Exist!");
        } finally {
            producer.shutdown();
        }
    }
}
