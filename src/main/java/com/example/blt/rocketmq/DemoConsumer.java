//package com.example.blt.rocketmq;
//
//import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
//import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
//import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
//import org.apache.rocketmq.common.message.MessageExt;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//
//
///**
// * @author hongjian.chen
// * @date 2019/6/25 16:26
// */
//
//@Service
//public class DemoConsumer {
//
//    @Value("${rocketmq.model}")
//    private String mode;
//    @Value("${rocketmq.name-server}")
//    private String namesrvAddr;
//
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    @PostConstruct
//    public void consumer() {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("blt_demo_group");
//        consumer.setNamesrvAddr(namesrvAddr);
//        try {
//            consumer.subscribe("user-topic", "*");
//            consumer.registerMessageListener((MessageListenerConcurrently) (list, context) -> {
//                try {
//                    for (MessageExt messageExt : list) {
//                        logger.warn("message [{}]", new String(messageExt.getBody()));
//                    }
//                } catch (Exception e) {
//                    logger.error(e.getMessage());
//                    return ConsumeConcurrentlyStatus.RECONSUME_LATER; //稍后再试
//                }
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS; //消费成功
//            });
//            consumer.start();
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
//    }
//}
