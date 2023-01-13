package com.example.rabbitmq.constant;

/**
 * @author: hj
 * @date: 2023/1/9
 * @time: 2:24 PM
 */
public class Constant {

    public static final String BUSINESS_EXCHANGE = "dead.letter.business.exchange";
    public static final String BUSINESS_QUEUE_A = "dead.letter.business.queuea";
    public static final String BUSINESS_QUEUE_B = "dead.letter.business.queueb";
    public static final String BUSINESS_QUEUE_A_ROUTING_KEY = "dead.letter.business.queuea";
    public static final String BUSINESS_QUEUE_B_ROUTING_KEY = "dead.letter.business.queueb";

    public static final String DEAD_LETTER_EXCHANGE = "dead.letter.deadletter.exchange";
    public static final String DEAD_LETTER_QUEUE_A_ROUTING_KEY = "dead.letter.deadletter.queuea";
    public static final String DEAD_LETTER_QUEUE_B_ROUTING_KEY = "dead.letter.deadletter.queueb";
    public static final String DEAD_LETTER_QUEUE_A = "dead.letter.deadletter.queuea";
    public static final String DEAD_LETTER_QUEUE_B = "dead.letter.deadletter.queueb";
}
