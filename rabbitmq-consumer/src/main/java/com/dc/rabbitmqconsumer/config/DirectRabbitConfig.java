package com.dc.rabbitmqconsumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author DC
 * @Date 2020-03-24
 */
@Configuration
public class DirectRabbitConfig {

    // 队列，起名为 TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue", true); //true 是否持久
    }

    // 交换机。 起名为 TestDirectCxchange
    @Bean
    DirectExchange TestDirectCxchange() {
        return new DirectExchange("TestDirectCxchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectCxchange()).with("TestDirectRouting");
    }

}
