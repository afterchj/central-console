package com.example.blt.service;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hongjian.chen
 * @date 2019/7/2 10:42
 */
public class ProducerService {

    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);

    private DefaultMQProducer defaultMQProducer;

    public void init() throws MQClientException {
        this.defaultMQProducer = new DefaultMQProducer("main_group");
        defaultMQProducer.setNamesrvAddr("119.3.49.192:9876");
        defaultMQProducer.start();
    }

    public void destroy() {
        defaultMQProducer.shutdown();
        // logger.info("rocketMQ生产者[producerGroup: " + producerGroup +
        // ",instanceName: " + instanceName + "]已停止");
    }

    public static void pushMsg(String... msg) {
        DefaultMQProducer producer = new DefaultMQProducer("blt_main_group");
        producer.setNamesrvAddr("119.3.49.192:9876");
        try {
            producer.start();
            Message message1 = new Message("blt_local_console_topic", msg[0].getBytes());
            Message message2 = new Message("blt_remote_console_topic", msg[0].getBytes());
            producer.send(message1);
            producer.send(message2);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        producer.shutdown();
    }
}
