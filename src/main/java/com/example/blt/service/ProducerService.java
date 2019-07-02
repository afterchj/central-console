package com.example.blt.service;

import com.example.blt.entity.dd.Topics;
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
        this.defaultMQProducer = new DefaultMQProducer("local_main_group");
        defaultMQProducer.setNamesrvAddr("119.3.49.192:9876");
        defaultMQProducer.setVipChannelEnabled(false);
        defaultMQProducer.start();
    }

    public void destroy() {
        defaultMQProducer.shutdown();
        // logger.info("rocketMQ生产者[producerGroup: " + producerGroup +
        // ",instanceName: " + instanceName + "]已停止");
    }

    public static void pushMsg(String... msg) {
        DefaultMQProducer producer = new DefaultMQProducer("blt_local_main_group");
        producer.setNamesrvAddr("119.3.49.192:9876");
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
