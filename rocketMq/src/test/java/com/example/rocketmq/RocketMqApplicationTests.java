package com.example.rocketmq;

import com.example.rocketmq.utils.RocketMqUtils;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SpringBootTest
class RocketMqApplicationTests {

    @Autowired
    RocketMQTemplate rocketMQTemplate;


    @Autowired
    RocketMqUtils rocketMqUtils;

    @Test
    void contextLoads() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hj");

//        rocketMqUtils.sendTransactionMessage("tag1", "123", "messageStr1");
    }

}
