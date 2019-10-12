package com.example.blt.config;

import com.example.blt.entity.dd.Topics;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hongjian.chen on 2019/10/12.
 */

@Configuration
public class RabbitConfig {

    @Bean
    public Queue cmdTopic() {
        return new Queue(Topics.CMD_TOPIC.getTopic());
    }

    @Bean
    public Queue consoleTopic() {
        return new Queue(Topics.CONSOLE_TOPIC.getTopic());
    }

    @Bean
    public Queue lightTopic() {
        return new Queue(Topics.LIGHT_TOPIC.getTopic());
    }

    @Bean
    public Queue hostTopic() {
        return new Queue(Topics.HOST_TOPIC.getTopic());
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("blt_topic_exchange");
    }

    @Bean
    Binding bindingExchangeLight() {
        return BindingBuilder.bind(lightTopic()).to(exchange()).with("topic.light.*");
    }

    @Bean
    Binding bindingExchangeCmd() {
        return BindingBuilder.bind(cmdTopic()).to(exchange()).with("topic.cmd.*");
    }

    @Bean
    Binding bindingExchangeHost() {
        return BindingBuilder.bind(hostTopic()).to(exchange()).with("topic.host.*");
    }

    @Bean
    Binding bindingExchangeConsole() {
        return BindingBuilder.bind(consoleTopic()).to(exchange()).with("topic.console.*");
    }

}
