package com.dc.rabbitmqprovider.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description 生产者推送消息的消息确认 回调函数的使用
 * @Author DC
 * @Date 2020-03-24
 */
@RestController
public class ConfirmMessageController {
    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     *  ① 消息推送到server，但是在server里找不到交换机
     *  把消息推送到名为‘non-existent-exchange’的交换机上（这个交换机是没有创建没有配置的）
     *
     *  结论：①这种情况触发的是 ConfirmCallback 回调函数
     * @return
     */
    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);

        rabbitTemplate.convertAndSend("\"non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }


    /**
     * ② 消息推送到server，找到交换机了，但是没找到队列
     *  把消息推送到名为‘lonelyDirectExchange’
     *  的交换机上（这个交换机是没有任何队列配置的）
     *
     * 结论：②这种情况触发的是 ConfirmCallback和RetrunCallback两个回调函数。
     * @return
     */
    @GetMapping("/TestMessageAck2")
    public String TestMessageAck2() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    /**
     *  ③消息推送到sever，交换机和队列啥都没找到
     *  这种情况其实一看就觉得跟①很像，没错 ，③和①情况回调是一致的，所以不做结果说明了。
     *  结论： ③这种情况触发的是 ConfirmCallback 回调函数。
     */


    /**
     * ④ 消息推送成功
     * 结论： ④这种情况触发的是 ConfirmCallback 回调函数。
     * @return
     */
    @GetMapping("/senMessageSuccess")
    public String sendMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String messageData = "test message, hello! now is " + createTime;
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
}
