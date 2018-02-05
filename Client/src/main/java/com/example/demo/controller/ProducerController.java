package com.example.demo.controller;

import com.example.demo.service.mq.kafka.KafkaSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018-01-30.
 */
@RestController
public class ProducerController {
    @Autowired
    private KafkaSendService kafkaSendService;

    @RequestMapping(value = "/send/{msg}", method = RequestMethod.GET)
    public void send(@PathVariable("msg") String msg){
        kafkaSendService.sendMessage(msg);
    }
}
