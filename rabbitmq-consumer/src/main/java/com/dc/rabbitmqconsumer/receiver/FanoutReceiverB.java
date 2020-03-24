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
@RabbitListener(queues = "fanout.B")
public class FanoutReceiverB {
    @RabbitHandler
    public void pricess(Map testMessage){
        System.out.println("FanoutReceiverB消费者收到消息  : " +testMessage.toString());
    }
}
