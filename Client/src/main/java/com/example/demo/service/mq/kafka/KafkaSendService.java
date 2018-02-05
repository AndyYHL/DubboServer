package com.example.demo.service.mq.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;

/**
 * Created by Administrator on 2018-01-30.
 */
@EnableBinding(Source.class)
public class KafkaSendService {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private Source source;

    /**
     * 发送MQ
     * @param msg
     */
    public void sendMessage(String msg) {
        try {
            source.output().send(MessageBuilder.withPayload(msg).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
