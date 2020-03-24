package com.dc.rabbitmqprovider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description     直连型交换机，根据消息携带的路由键将消息投递给对应队列
 *                  大致流程，有一个队列绑定到一个直连交换机上，同时赋予一个路由键 routing key 。
 *                  然后当一个消息携带着路由值为X，这个消息通过生产者发送给交换机时，
 *                  交换机就会根据这个路由值X去寻找绑定值也是X的队列
 * @Author DC
 * @Date 2020-03-24
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 操作简介
     *
     * 1. 注册一个队列Queue，并命名
     * 2. 注册一个交换机Exchange，并命名
     * 3. 将队列绑定到交换机上，并且定义一个“钥匙”routingKey
     *      发送消息时，要填写自命名的交换机，和自命名的钥匙routingKey，
     *          这样消息就会被发送到指定“钥匙”routingKey的交换机上，并且存储在绑该定交换机的队列Queue中
     *
     * 4. 我们消费者同样配置 队列和交换机，
     *      然后用 @RabbitListener(queues = "之前自命名的队列名")注解class类，该类就会自动监听指定队列
     *          用 @RabbitHandler 注解 方法，该方法就可以接收到队列中的消息，并自定义处理消息的操作
     *
     */

    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        return new Queue("TestDirectQueue", true); //true 是否持久
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    DirectExchange TestDirectExchange() {
        return new DirectExchange("TestDirectExchange");
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    Binding bindingDirect() {
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("TestDirectRouting");
    }

    /**
     * 测试 ②消息推送到server，找到交换机了，但是没找到队列
     * @return
     */
    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }
}
