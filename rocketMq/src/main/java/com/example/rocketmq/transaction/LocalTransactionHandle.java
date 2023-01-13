package com.example.rocketmq.transaction;

/**
 * @author: hj
 * @date: 2023/1/12
 * @time: 5:10 PM
 */
public interface LocalTransactionHandle {

    /**
     * 处理本地事务
     *
     * @param id
     * @param jsonData
     */
    void handleLocalTransaction(String id, String jsonData);

}
