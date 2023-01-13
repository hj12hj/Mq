package com.example.rabbitmq.handle;

import com.rabbitmq.client.Channel;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: hj
 * @date: 2023/1/11
 * @time: 10:11 AM
 */
@Slf4j
@Component
public class DeadLetterAckReceiver {

    // 业务队列手动确认消息
    @RabbitListener(queues = "dead.letter.business.queuea")
    @RabbitHandler
    public void deadLetterReceiver1(@NotNull Message message, Channel channel) {
        try {
            // 直接拒绝消费该消息，后面的参数一定要是false，否则会重新进入业务队列，不会进入死信队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            log.info("拒绝签收...消息的路由键为：" + message.getMessageProperties().getReceivedRoutingKey());
        } catch (Exception e) {
            log.info("消息拒绝签收失败", e);
        }
    }

//    // 业务队列手动确认消息
//    @RabbitListener(queues = "dead.letter.business.queueb")
//    @RabbitHandler
//    public void deadLetterReceiver2(@NotNull Message message, Channel channel) {
//        try {
//            // 直接拒绝消费该消息，后面的参数一定要是false，否则会重新进入业务队列，不会进入死信队列
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
//            log.info("拒绝签收...消息的路由键为：" + message.getMessageProperties().getReceivedRoutingKey());
//        } catch (Exception e) {
//            log.info("消息拒绝签收失败", e);
//        }
//    }
//
//    @RabbitListener(queues = {"dead.letter.deadletter.queuea"})
//    @RabbitHandler
//    public void deadLetterConsumer1(@NotNull Message message, @NotNull Channel channel) {
//        String msg = message.toString();
//        String[] msgArray = msg.split("'");
//
//        log.info("死信队列接收到的消息为：" );
//        log.info("消费的主题消息来自：" + message.getMessageProperties().getConsumerQueue());
//        try {
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
//        } catch (IOException e) {
//            log.info("死信队列中消息的消费失败", e);
//            e.printStackTrace();
//        }
//    }


}
