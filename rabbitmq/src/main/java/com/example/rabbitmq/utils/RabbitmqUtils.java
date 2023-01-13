package com.example.rabbitmq.utils;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 9:17 AM
 */
@Component
public class RabbitmqUtils {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送过期消息
     *
     * @param exchange
     * @param routingKey
     * @param messageStr
     * @param delayTime
     */
    public void sendDelayMessage(String exchange, String routingKey, String messageStr, String id, Long delayTime) {

        rabbitTemplate.convertAndSend(exchange, routingKey, messageStr, message -> {
            message.getMessageProperties().setExpiration(delayTime.toString());
            message.getMessageProperties().setContentEncoding("utf-8");
            return message;
        }, new CorrelationData(id));
    }


    /**
     * 携带头部信息发送消息
     *
     * @param exchange
     * @param routingKey
     * @param messageStr
     * @param map
     */
    public void sendMessageWithHeader(String exchange, String routingKey, String messageStr, String id, Map<String, Object> map) {
        rabbitTemplate.convertAndSend(exchange, routingKey, messageStr, message -> {
            message.getMessageProperties().setHeaders(map);
            message.getMessageProperties().setContentEncoding("utf-8");
            return message;
        }, new CorrelationData(id));
    }


    /**
     * 发送基础消息
     *
     * @param exchange
     * @param routingKey
     * @param messageStr
     * @param id
     */
    public void sendCommonMessage(String exchange, String routingKey, String messageStr, String id) {
        rabbitTemplate.convertAndSend(exchange, routingKey, messageStr, new CorrelationData(id));
    }


    /**
     * 发送类别消息
     *
     * @param exchange
     * @param routingKey
     * @param messageStr
     * @param id
     * @param type
     */
    public void sendTypeMessage(String exchange, String routingKey, String messageStr, String id, String type) {
        rabbitTemplate.convertAndSend(exchange, routingKey, messageStr, message -> {
            message.getMessageProperties().getHeaders().put("type", type);
            return message;
        }, new CorrelationData(id));
    }


}
