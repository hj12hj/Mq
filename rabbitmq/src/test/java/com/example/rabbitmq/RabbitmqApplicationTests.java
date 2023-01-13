package com.example.rabbitmq;

import com.hj.AddWaterMarkUtil;
import com.hj.core.AddWaterMark;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitmqApplicationTests {



    @Test
    void contextLoads() {
        AddWaterMark addWaterMark = new AddWaterMarkUtil();
        System.out.println(addWaterMark);
    }

}
