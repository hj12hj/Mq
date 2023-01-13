package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: hj
 * @date: 2023/1/9
 * @time: 2:09 PM
 */
@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        /*设置开启Mandatory才能触发回调函数，无论消息推送结果怎么样都强制调用回调函数*/
        rabbitTemplate.setMandatory(true);
        /*消息发送到Exchange的回调，无论成功与否*/
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("ConfirmCallback：" + "相关数据：" + correlationData);
            log.info("ConfirmCallback：" + "确认情况：" + ack);
            log.info("ConfirmCallback：" + "原因：" + cause);
        });

        /*消息从Exchange路由到Queue失败的回调*/
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            //todo 处理消息丢失的情况
            log.info("ReturnCallback：" + "消息：" + message);
            log.info("ReturnCallback：" + "回应码：" + replyCode);
            log.info("ReturnCallback：" + "回应信息：" + replyText);
            log.info("ReturnCallback：" + "交换机：" + exchange);
            log.info("ReturnCallback：" + "路由键：" + routingKey);
        });


        return rabbitTemplate;
    }

}
