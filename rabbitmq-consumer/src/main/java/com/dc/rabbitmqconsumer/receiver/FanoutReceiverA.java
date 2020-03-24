package com.dc.rabbitmqconsumer.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Description
 * @Author DC
 * @Date 2020-03-24
 */
@Component
@RabbitListener(queues = "fanout.A")
public class FanoutReceiverA {
    @RabbitHandler
    public void pricess(Map testMessage){
        System.out.println("FanoutReceiverA消费者收到消息  : " +testMessage.toString());
    }
}
