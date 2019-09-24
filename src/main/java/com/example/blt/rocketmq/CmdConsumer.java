package com.example.blt.rocketmq;

import com.example.blt.netty.ClientMain;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author hongjian.chen
 * @date 2019/6/25 16:26
 */

@Service
@RocketMQMessageListener(topic = "blt_cmd_console_topic", consumerGroup = "blt_dev_consumer_cmd_group")
public class CmdConsumer implements RocketMQListener<String> {

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
