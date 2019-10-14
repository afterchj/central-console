package com.example.blt.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author hongjian.chen
 * @date 2019/10/12 15:37
 */
public class ProducerService {

    private static final String EXCHANGE_NAME = "blt_topic_exchange";
    private static Logger logger = LoggerFactory.getLogger(ProducerService.class);
    public static Connection connection;
    public static Channel channel;

    static {
        connection = ConnectionUtil.getConnection();
        try {
            channel = connection.createChannel();
        } catch (IOException e) {
            logger.error("error {}", e.getMessage());
        }
    }


    public static void pushMsg(String... msg) {
        try {
            channel.basicPublish(EXCHANGE_NAME, msg[0], false, false, null, msg[1].getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
