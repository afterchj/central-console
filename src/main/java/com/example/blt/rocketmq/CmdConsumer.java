package com.example.blt.rocketmq;

import com.example.blt.netty.ClientMain;
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
@RocketMQMessageListener(topic = "blt_cmd_console_topic", consumerGroup = "my_consumer_cmd_group")
public class CmdConsumer implements RocketMQListener<String> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private ClientMain clientMain = new ClientMain();

    @Override
    public void onMessage(String message) {
        try {
            logger.warn("message=" + message);
            clientMain.sendCron(8001, message, false);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
