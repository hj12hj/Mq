package com.example.rabbitmq.controller;

import com.example.rabbitmq.constant.Constant;
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
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send")
    public String send() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: dead.letter.business.exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        CorrelationData correlationData = new CorrelationData(messageId);
        rabbitTemplate.convertAndSend("Constant.BUSINESS_EXCHANGE", Constant.BUSINESS_QUEUE_A_ROUTING_KEY, map,new CorrelationData(messageId));
        logger.info("发送消息：{}", map);
        return "success";
    }

}
