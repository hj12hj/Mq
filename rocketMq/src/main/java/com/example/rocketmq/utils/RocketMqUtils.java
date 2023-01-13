package com.example.rocketmq.utils;

import com.alibaba.fastjson.JSONObject;
import com.example.rocketmq.entity.BatchMessage;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: hj
 * @date: 2023/1/12
 * @time: 11:27 AM
 */
@Component
public class RocketMqUtils {

    Logger logger = org.slf4j.LoggerFactory.getLogger(RocketMqUtils.class);

    @Autowired
    RocketMQTemplate rocketMQTemplate;


    /**
     * 主题
     */
    @Value("${rocketmq.producer.topic}")
    private String topic;


    /**
     * 发送同步消息
     *
     * @param tag
     * @param key
     * @param messageStr
     */
    public void sendSyncMessage(String tag, String key, String messageStr) {
        logger.info("发送同步消息,tag{},key{},messageStr{}", tag, key, messageStr);
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        String tags = String.format("%s:%s", topic, tag);
        SendResult sendResult = rocketMQTemplate.syncSend(tags, message);
        logger.info("发送同步消息结果,sendResult{}", sendResult);
    }

    /**
     * 发送异步消息
     *
     * @param tag
     * @param key
     * @param messageStr
     */
    public void sendAsyncMessage(String tag, String key, String messageStr) {
        logger.info("发送异步消息,tag{},key{},messageStr{}", tag, key, messageStr);
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        String tags = String.format("%s:%s", topic, tag);
        rocketMQTemplate.asyncSend(tags, message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("发送异步消息成功,sendResult{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                //todo 补偿措施
                logger.info("发送异步消息失败,throwable{}", throwable);
            }
        });
    }


    /**
     * 发送单向消息  无需关注是否发送成功
     *
     * @param tag
     * @param key
     * @param messageStr
     */
    public void sendOneWayMessage(String tag, String key, String messageStr) {
        logger.info("发送单向消息,tag: {},key: {},message: {}", tag, key, messageStr);
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        String tags = String.format("%s:%s", topic, tag);
        rocketMQTemplate.sendOneWay(tags, message);
    }


    /**
     * 顺序消息
     *
     * @param tag
     * @param key
     * @param messageStr
     */
    public void sendOrderlyMessage(String tag, String key, String messageStr) {
        logger.info("发送顺序消息,tag: {},key: {},message: {}", tag, key, messageStr);
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        String tags = String.format("%s:%s", topic, tag);

        rocketMQTemplate.setMessageQueueSelector(new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> list, org.apache.rocketmq.common.message.Message message, Object o) {
                int queueNum = Integer.valueOf(String.valueOf(o)) % list.size();
                logger.info(String.format("顺序消息 queueNum : %s, message : %s", queueNum, new String(message.getBody())));
                return list.get(queueNum);
            }
        });
        rocketMQTemplate.syncSendOrderly(tags, message, key);
    }

    /**
     * 现在RocketMq并不支持任意时间的延时，需要设置几个固定的延时等级，从1s到2h分别对应着等级1到18
     * 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
     *
     * @param tag
     * @param key
     * @param messageStr
     * @param delayLevel
     */
    public void sendDelayMessage(String tag, String key, String messageStr, int delayLevel) {
        logger.info("发送延迟消息,tag: {},key: {},message: {}", tag, key, messageStr);
        Message<String> message = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .build();
        String tags = String.format("%s:%s", topic, tag);
        rocketMQTemplate.syncSend(tags, message, 1000, delayLevel);
    }


    /**
     * 同时发送10个单向消息（真正的批量
     * 批量下发消息到broker,不支持消息顺序操作，并且对消息体有大小限制（不超过4M）
     *
     * @param tag
     * @param messages
     */
    public void sendBatchMessage(String tag, List<BatchMessage> messages) {
        logger.info("发送批量消息,tag: {},messages: {}", tag, messages);
        String tags = String.format("%s:%s", topic, tag);
        List<Message> messageList = new ArrayList<>();
        messages.forEach(message -> {
            Message<String> msg = MessageBuilder.withPayload(message.getJsonData())
                    .setHeader(RocketMQHeaders.KEYS, message.getId())
                    .build();
            messageList.add(msg);
        });
        // 批量下发消息到broker,不支持消息顺序操作，并且对消息体有大小限制（不超过4M）
        ListSplitter splitter = new ListSplitter(messages, 1024 * 1024 * 4);
        while (splitter.hasNext()) {
            List<Message> listItem = splitter.next();
            rocketMQTemplate.syncSend(tags, listItem);
        }

    }

    /**
     * 发送头部判断消息
     *
     * @param tag
     * @param key
     * @param messageStr
     * @param map
     */
    public void sendSqlMessage(String tag, String key, String messageStr, Map<String, Object> map) {
        logger.info("发送头部判断消息,tag: {},key: {},message: {}", tag, key, messageStr);
        logger.info("map: {}", map);
        String tags = String.format("%s:%s", topic, tag);
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(messageStr).setHeader(RocketMQHeaders.KEYS, key);
        map.entrySet().forEach(entry -> {
            logger.info("key: {},value: {}", entry.getKey(), entry.getValue());
            stringMessageBuilder.setHeader(entry.getKey(), entry.getValue());
        });
        rocketMQTemplate.syncSend(tags, stringMessageBuilder.build());
    }


    /**
     * 事务消息
     *
     * @param tag
     * @param key
     * @param messageStr
     */
    public void sendTransactionMessage(String tag, String key, String messageStr, String type, Map<String, Object> map) {
        logger.info("发送事务消息,tag: {},key: {},message: {}", tag, key, messageStr);
        String tags = String.format("%s:%s", topic, tag);
        if (type != null) {
            map.put("type", type);
        }
        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(messageStr)
                .setHeader(RocketMQHeaders.KEYS, key)
                .setHeader(RocketMQHeaders.TRANSACTION_ID, key);
        map.entrySet().forEach(entry -> {
            logger.info("key: {},value: {}", entry.getKey(), entry.getValue());
            stringMessageBuilder.setHeader(entry.getKey(), entry.getValue());
        });
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(tags, stringMessageBuilder.build(), null);
        logger.info("事务消息发送结果: {}", JSONObject.toJSONString(transactionSendResult));
    }


}
