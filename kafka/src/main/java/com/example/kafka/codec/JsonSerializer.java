package com.example.kafka.codec;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.kafka.common.serialization.Serializer;
import org.slf4j.Logger;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 11:12 AM
 */
public class JsonSerializer implements Serializer {

    Logger logger = org.slf4j.LoggerFactory.getLogger(JsonSerializer.class);

    @Override
    public byte[] serialize(String s, Object o) {
        String json = JSON.toJSONString(o);
        logger.info("json:{}", json);
        return json.getBytes();
    }
}

