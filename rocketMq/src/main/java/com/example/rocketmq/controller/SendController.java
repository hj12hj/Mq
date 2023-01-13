package com.example.rocketmq.controller;

import com.example.rocketmq.utils.RocketMqUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: hj
 * @date: 2023/1/12
 * @time: 3:06 PM
 */
@RestController
public class SendController {

    @Autowired
    private RocketMqUtils rocketMqUtils;

    @GetMapping("/sendSQl")
    public void sendSql() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hj");

        rocketMqUtils.sendOneWayMessage("tag1", "123", "messageStr1");
    }

    @GetMapping("/sendTag")
    public void sendTag(String key) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "hj");

        rocketMqUtils.sendTransactionMessage("tag1", key, "messageStr1", null, map);
    }

}
