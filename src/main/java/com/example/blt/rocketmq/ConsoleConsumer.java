//package com.example.blt.rocketmq;
//
//import com.alibaba.fastjson.JSON;
//import com.example.blt.config.WebSocket;
//import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
//import org.apache.rocketmq.spring.core.RocketMQListener;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.Map;
//
//
///**
// * @author hongjian.chen
// * @date 2019/6/25 16:26
// */
//
//@Service
//@RocketMQMessageListener(topic = "blt_console_topic", consumerGroup = "blt_cloud_consumer_console_group")
//public class ConsoleConsumer implements RocketMQListener<String> {
//
//    @Resource
//    private SqlSessionTemplate sqlSessionTemplate;
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//    @Value("${rocketmq.model}")
//    private String mode;
//
//    @Override
//    public void onMessage(String message) {
//        if ("local".equals(mode)) return;
//        try {
//            Map map = JSON.parseObject(message);
//            sqlSessionTemplate.selectOne("console.saveConsole", map);
//            WebSocket.sendMessage(map);
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }
//}
