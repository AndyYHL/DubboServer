package com.example.demo.mq.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018-01-30.
 */
@Component
@EnableScheduling
//注解表示该消息消费者监听hello这个消息队列，@RabbitHandler注解则表示process方法是用来处理接收到的消息的，我们这里收到消息后直接打印即可。
@RabbitListener(queues = "hello",containerFactory = "rabbitListenerContainerFactory")
public class RabbitReceiver {
    @RabbitHandler
    public void process(@Payload String msg) {
        int a= 12;
        System.out.println("Receiver:"+msg);
    }
}
