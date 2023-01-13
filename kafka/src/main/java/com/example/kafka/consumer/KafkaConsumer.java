package com.example.kafka.consumer;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 10:29 AM
 */
@Component
public class KafkaConsumer {


    Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = {"test"})
    public void onMessage1(@Payload String message,
                           @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                           @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                           @Header("type") String type,
                           Acknowledgment ack) {

        logger.info("消费消息,topic:{},partition:{},type:{},message:{}", topic, partition, type, message);

        try {

            ack.acknowledge();
        } catch (Exception e) {
            logger.error("消费者消费异常", e.getMessage());
            throw new RuntimeException("消费消息失败");

        }


    }




    @KafkaListener(topics = {"test.DLT"})
    public void onMessage(@Payload String message,
                          @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
                          @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                          @Header("type") String type
    ) {

        logger.info("死信消息,topic:{},partition:{},type:{},message:{}", topic, partition, type, message);


    }


}
