package com.example.kafka.utils;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 10:22 AM
 */
@Component
public class KafkaProducerListener implements ProducerListener {

    Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaProducerListener.class);

    @Override
    public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
        logger.info("发送异步消息成功,producerRecord{},recordMetadata{}", producerRecord, recordMetadata);
    }

    @Override
    public void onError(ProducerRecord producerRecord, RecordMetadata recordMetadata, Exception exception) {
        logger.info("发送异步消息失败,producerRecord{},recordMetadata{},exception{}", producerRecord, recordMetadata, exception);
    }
}
