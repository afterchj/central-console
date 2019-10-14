package com.example.blt.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.example.blt.config.WebSocket;
import com.example.blt.netty.ClientMain;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @author hongjian.chen
 * @date 2019/10/14 15:37
 */
@Component
public class MainConsumer {

    private Logger logger = LoggerFactory.getLogger(MainConsumer.class);
    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    @RabbitListener(queues = "blt_console_topic")
    public void processConsole(Message message) {
        String roteKey = message.getMessageProperties().getReceivedRoutingKey();
        String msg = new String(message.getBody());
        logger.warn("routeKey {} message {} ", roteKey, msg);
        try {
            Map map = JSON.parseObject(msg);
            sqlSessionTemplate.selectOne("console.saveConsole", map);
            WebSocket.sendMessage(map);
//            if ("topic.console.local".equals(roteKey)) {
//                WebSocket.sendMessage(map);
//            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RabbitListener(queues = "blt_console_cmd")
    public void processCommand(Message message) {
        String roteKey = message.getMessageProperties().getReceivedRoutingKey();
        String msg = new String(message.getBody());
        logger.warn("routeKey {} message {} ", roteKey, msg);
        try {
            logger.warn("message=" + message);
            ClientMain.sendCron(msg);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RabbitListener(queues = "blt_console_host")
    public void processHost(Message message) {
        String roteKey = message.getMessageProperties().getReceivedRoutingKey();
        String msg = new String(message.getBody());
        logger.warn("routeKey {} message {} ", roteKey, msg);
        try {
            Map map = JSON.parseObject(msg);
            sqlSessionTemplate.selectOne("console.saveUpdateHosts", map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    @RabbitListener(queues = "blt_console_light")
    public void processLight(Message message) {
        String roteKey = message.getMessageProperties().getReceivedRoutingKey();
        String msg = new String(message.getBody());
        logger.warn("routeKey {} message {} ", roteKey, msg);
        try {
            Map map = JSON.parseObject(msg);
            sqlSessionTemplate.selectOne("console.saveLight", map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
