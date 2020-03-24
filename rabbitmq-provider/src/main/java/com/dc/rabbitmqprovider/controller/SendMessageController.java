package com.dc.rabbitmqprovider.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 发送消息
 * @Author DC
 * @Date 2020-03-24
 */
@RestController
public class SendMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate; //使用RabbitTemplate,这提供了接收/发送等等方法

    // Direct交换器
    @GetMapping("/senDirectMessage")
    public String sendMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String messageData = "test message, hello! now is " + createTime;
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    // Top交换器
    @GetMapping("/sendTopicMessage1")
    public String sendTopicMessage1() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String messageData = "message: M A N ,now is " + createTime;
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        // 发送消息
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "topicMan ok";
    }

    // Top交换器
    @GetMapping("/sendTopicMessage2")
    public String sendTopicMessage2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String messageData = "message: woman is all ,now is " + createTime;
        Map<String, Object> maWonMap = new HashMap<>();
        maWonMap.put("messageId", messageId);
        maWonMap.put("messageData", messageData);
        maWonMap.put("createTime", createTime);
        // 发送消息
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", maWonMap);
        return "topicWoman ok";
    }

    // fanout交换器
    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        // 发送消息
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

}
