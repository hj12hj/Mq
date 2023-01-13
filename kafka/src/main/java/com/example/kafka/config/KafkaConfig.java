package com.example.kafka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.*;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.backoff.BackOff;
import org.springframework.util.backoff.FixedBackOff;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 12:12 PM
 */
@Configuration
public class KafkaConfig {

//    @Bean
//    @Primary
//    public ErrorHandler kafkaErrorHandler(KafkaTemplate<String, Object> template) {
//        // <1> 创建 DeadLetterPublishingRecoverer 对象
//        ConsumerRecordRecoverer recoverer = new DeadLetterPublishingRecoverer(template);
//        // <2> 创建 FixedBackOff 对象   设置重试间隔 10秒 次数为 1 次
//        // 创建 DeadLetterPublishingRecoverer 对象，它负责实现，在重试到达最大次数时，Consumer 还是消费失败时，该消息就会发送到死信队列。
//        // 注意，正常发送 1 次，重试 1 次，等于一共 2 次
//        BackOff backOff = new FixedBackOff(1 * 1000L, 3L);
//        // <3> 创建 SeekToCurrentErrorHandler 对象
//        return new SeekToCurrentErrorHandler(recoverer, backOff);
//    }


    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
//        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        //最大重试三次
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), new FixedBackOff(1 * 1000L, 1L)));
        return factory;

    }

}
