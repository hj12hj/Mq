package com.example.rocketmq.entity;

/**
 * @author: hj
 * @date: 2023/1/12
 * @time: 2:44 PM
 */
public class BatchMessage {
    String id;
    String jsonData;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    public String toString() {
        return "BatchMessage{" +
                "id='" + id + '\'' +
                ", jsonData='" + jsonData + '\'' +
                '}';
    }
}
