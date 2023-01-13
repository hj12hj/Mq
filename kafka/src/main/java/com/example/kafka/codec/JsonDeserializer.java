package com.example.kafka.codec;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * @author: hj
 * @date: 2023/1/13
 * @time: 11:15 AM
 */
public class JsonDeserializer implements Deserializer {
    private final static Logger logger = LoggerFactory.getLogger(JsonDeserializer.class);

    @Override
    public Object deserialize(String s, byte[] bytes) {
        try {
            String json = new String(bytes, "utf-8");
            logger.info("json:{}", json);
            return JSON.parse(json);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}