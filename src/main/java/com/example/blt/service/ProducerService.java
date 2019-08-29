package com.example.blt.service;

import com.example.blt.exception.NoTopicException;
import com.example.blt.utils.PropertiesUtil;
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
        String addr= PropertiesUtil.getValue("rocketmq.name-server");
        DefaultMQProducer producer = new DefaultMQProducer("blt_local_main_group");
        producer.setSendMsgTimeout(100);
        producer.setInstanceName(UUID.randomUUID().toString());
        producer.setNamesrvAddr(addr);
        producer.setVipChannelEnabled(false);
        try {
            producer.start();
            Message message = new Message(msg[0], msg[1].getBytes());
            producer.send(message);
        } catch (Exception e) {
            logger.error("pushMsg error [{}]",e.getMessage());
            throw new NoTopicException("Topic Not Exist!");
        } finally {
            producer.shutdown();
        }
    }
}
