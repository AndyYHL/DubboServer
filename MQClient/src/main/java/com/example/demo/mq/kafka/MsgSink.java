package com.example.demo.mq.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by Administrator on 2018-01-30.
 */
@EnableBinding(Sink.class)
public class MsgSink {

    @StreamListener(Sink.INPUT)
    public void messageSink(Object payload) {
        System.out.println("Received: " + payload);
    }
}
