package com.example.kafka.utils;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.TimeUnit;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 10:19 AM
 */
@Component
public class KafkaUtils {

    Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaUtils.class);
    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    ProducerListener producerListener;


    /**
     * 发送异步消息
     *
     * @param topic
     * @param key
     * @param type
     * @param messageStr
     */
    public void sendAsyncMessage(String topic, String key, String type, String messageStr) {
        Message<String> message = MessageBuilder
                .withPayload(messageStr)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader("type", type)
                .build();
        kafkaTemplate.setProducerListener(producerListener);
        kafkaTemplate.send(message);
    }

    /**
     * 发送同步消息
     *
     * @param topic
     * @param key
     * @param type
     * @param messageStr
     */
    public void sendSyncMessage(String topic, String key, String type, String messageStr) {
        Message<String> message = MessageBuilder
                .withPayload(messageStr)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader("type", type)
                .build();
        kafkaTemplate.setProducerListener(producerListener);
        ListenableFuture<SendResult<String, Object>> send = kafkaTemplate.send(message);
        try {
            SendResult<String, Object> stringObjectSendResult = send.get(3, TimeUnit.SECONDS);
            logger.info("发送同步消息结果,stringObjectSendResult{}", stringObjectSendResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 事务消息
     *
     * @param topic
     * @param key
     * @param type
     * @param messageStr
     * @param localHandle
     */
    public void sendTransactionMessage(String topic, String key, String type, String messageStr, LocalHandle localHandle) {
        Message<String> message = MessageBuilder
                .withPayload(messageStr)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .setHeader(KafkaHeaders.MESSAGE_KEY, key)
                .setHeader("type", type)
                .build();
        kafkaTemplate.setProducerListener(producerListener);
        kafkaTemplate.executeInTransaction(operations -> {
            localHandle.handle();
            operations.send(message);
            return true;
        });
    }

}
