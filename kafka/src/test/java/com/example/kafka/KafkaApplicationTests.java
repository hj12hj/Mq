package com.example.kafka;

import com.example.kafka.utils.KafkaUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;

import javax.annotation.Resource;

@SpringBootTest
class KafkaApplicationTests {

    @Resource
    private KafkaUtils kafkaUtils;

    @Test
    void contextLoads() {
        kafkaUtils.sendAsyncMessage("test", "123", "hj", "messageStr1");
        kafkaUtils.sendAsyncMessage("test", "123", "hj", "messageStr1");
    }


    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;


    @Test
    void name() {
        kafkaTemplate.send("test", 0, "key", "key=" + "key" + "，msg=指定0号分区");
        kafkaTemplate.send("test", 1, "key", "key=" + "key" + "，msg=指定0号分区");
    }
}
