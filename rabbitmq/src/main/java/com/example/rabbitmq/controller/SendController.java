package com.example.rabbitmq.controller;

import com.example.rabbitmq.constant.Constant;
import com.example.rabbitmq.utils.RabbitmqUtils;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
 * @author: hj
 * @date: 2023/1/11
 * @time: 10:27 AM
 */
@RestController
public class SendController {
    Logger logger = org.slf4j.LoggerFactory.getLogger(SendController.class);

    @Autowired
    private RabbitmqUtils rabbitmqUtils;

    @GetMapping("/send")
    public String send() {

        rabbitmqUtils.sendTypeMessage(Constant.BUSINESS_EXCHANGE, Constant.BUSINESS_QUEUE_A_ROUTING_KEY, "messageStr1", "123", "hj");


        return "success";
    }

}
