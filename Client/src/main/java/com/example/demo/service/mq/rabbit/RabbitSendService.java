package com.example.demo.service.mq.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Administrator on 2018-01-30.
 */
@Component
public class RabbitSendService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     * 发送MQ
     * @param msg
     */
    public void sendMessage(String key,String msg) {
        try {
            msg += "hello rabbitmq:"+new Date();
            System.out.println("Sender:"+msg);
            //rabbitTemplate.convertAndSend(key, msg);
            this.rabbitTemplate.convertAndSend("hello", msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
