package com.example.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @author: hj
 * @date: 2023/1/12
 * @time: 10:30 AM
 */
@Component
@RocketMQMessageListener(topic = "test-topic", consumerGroup = "consumer-group",
//        selectorExpression = "tag1",selectorType = SelectorType.TAG,
        messageModel = MessageModel.CLUSTERING, consumeMode = ConsumeMode.CONCURRENTLY)
public class MessageConsumer implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(String message) {
        System.out.println("----------接收到rocketmq消息:" + message);
        // rocketmq会自动捕获异常回滚  (官方默认会重复消费16次)
        int a = 1 / 0;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(2);
        // 如下，设置其它consumer相关属性
        defaultMQPushConsumer.setPullBatchSize(16);

    }
}
