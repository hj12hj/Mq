package com.example.kafka.controller;

import com.example.kafka.utils.KafkaUtils;
import com.example.kafka.utils.LocalHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 1:09 PM
 */
@RestController
public class SendController {


    @Autowired
    KafkaUtils kafkaUtils;


    @GetMapping("/send")
    @Transactional(rollbackFor = Exception.class)
    public void send() {
//        kafkaUtils.sendTransactionMessage("test", "123", "hj", "messageStr1", new LocalHandle() {
//            @Override
//            public void handle() {
//                System.out.println("执行本地事务");
//                throw new RuntimeException("执行本地事务失败");
//            }
//        });
        kafkaUtils.sendAsyncMessage("test", "123", "hj", "messageStr1");
//        int a = 1 / 0;
    }

}
