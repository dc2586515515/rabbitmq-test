參考自CSDN：https://blog.csdn.net/qq_35387940/article/details/100514134

### 代码简介
    
##### 1. 注册一个队列Queue，并命名
##### 2. 注册一个交换机Exchange，并命名
#####  3. 将队列绑定到交换机上，并且定义一个“钥匙”routingKey
#####  发送消息时，要填写自命名的交换机，和自命名的钥匙routingKey，
#####     这样消息就会被发送到指定“钥匙”routingKey的交换机上，并且存储在绑该定交换机的队列Queue中
    
##### 4. 我们消费者同样配置 队列和交换机，
##### 然后用 @RabbitListener(queues = "之前自命名的队列名")注解class类，该类就会自动监听指定队列
##### 用 @RabbitHandler 注解 方法，该方法就可以接收到队列中的消息，并自定义处理消息的操作