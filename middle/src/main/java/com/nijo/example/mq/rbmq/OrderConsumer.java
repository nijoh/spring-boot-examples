package com.nijo.example.mq.rbmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class OrderConsumer {
    @RabbitListener(queues = {"dlx_order_queue"})
    @RabbitHandler
    public void orderConsumerHandle(String str, Channel channel, Message message) throws IOException {
        try {
           //获取消息Message对象 MessageBuilder.withBody(str.getBytes(StandardCharsets.UTF_8)).build();
            System.out.println("消息ID:"+message.getMessageProperties().getMessageId()+"，消息内容:"+str);
            throw new Exception("11");
            //channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);

        } catch (Exception e) {
            e.printStackTrace();
            //失败 true 重回队列 false 丢弃
            //channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            //channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
            //nack表示拒绝消息。multiple表示拒绝指定了delivery_tag的所有未确认的消息，requeue表示不是重回队列 如果队列绑定死信队列 会路由到死信队列
        }
    }
}
