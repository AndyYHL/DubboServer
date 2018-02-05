package com.example.demo.mq.kafka;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;

/**
 * Created by Administrator on 2018-01-30.
 */
@EnableBinding(Processor.class)
public class TransformProcessor {
    private static final String TRANSFORMATION_VALUE = "HI";

/*    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
//	@ServiceActivator(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
//	@Transformer(inputChannel = Processor.INPUT, outputChannel = Processor.OUTPUT)
    public Converters.Bar receive(Converters.Bar barMessage) {
        System.out.println("******************");
        System.out.println("At the transformer");
        System.out.println("******************");
        System.out.println("Received value "+ barMessage.getValue() + " of type " + barMessage.getClass());
        System.out.println("Transforming the value to " + TRANSFORMATION_VALUE + " and with the type " + barMessage.getClass());
        barMessage.setValue(TRANSFORMATION_VALUE);
        return barMessage;
    }*/
}
