package com.example.blt.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

public class Main {

    public static void main(String[] args) {
//        consumerMsg();
    }


    public static void consumerMsg() {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("remote_consumer_group");
        consumer.setNamesrvAddr("119.3.49.192:9876");
        try {
            consumer.subscribe("blt_console_topic", "");
            consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
                for (MessageExt msg : msgs) {
                    System.out.println("body=" + new String(msg.getBody()));
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        System.out.println("Consumer Started.");
    }
}
