package com.dc.rabbitmqconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author DC
 * @Date 2020-03-24
 */
@Configuration
public class TopicRabbitConfig {
    // 绑定键
    private final static String man = "topic.man";
    private final static String woman = "topic.woman";

    @Bean
    public Queue firstQueue(){
        return new Queue(TopicRabbitConfig.man);
    }

    @Bean
    public Queue secondQueue(){
        return new Queue(TopicRabbitConfig.woman);
    }

    @Bean
    TopicExchange testExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    Binding bindingExchangeMeaasge1(){
        return BindingBuilder.bind(firstQueue()).to(testExchange()).with(man);
    }

    @Bean
    Binding bindingExchangeMeaasge2(){
        return BindingBuilder.bind(secondQueue()).to(testExchange()).with("topic.#");
    }
}
