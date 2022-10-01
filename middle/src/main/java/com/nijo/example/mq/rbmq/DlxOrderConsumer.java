package com.nijo.example.mq.rbmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 死信订单消费者
 * */
@Component
public class DlxOrderConsumer {
    @RabbitListener(queues = {"dlx_order_queue"})
    @RabbitHandler
    public void orderConsumerHandle(String str, Channel channel, Message message) throws IOException {
        try {
           //获取消息Message对象 MessageBuilder.withBody(str.getBytes(StandardCharsets.UTF_8)).build();
            System.out.println("消息ID:"+message.getMessageProperties().getMessageId()+"，消息内容:"+str);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),true);

        } catch (Exception e) {
            e.printStackTrace();
            //失败 true 重回队列 false 丢弃
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
            //channel.basicNack(msg.getMessageProperties().getDeliveryTag(),false,false);
            //nack表示拒绝消息。multiple表示拒绝指定了delivery_tag的所有未确认的消息，requeue表示不是重回队列 如果队列绑定死信队列 会路由到死信队列

            //重回队尾 默认NAck、Reject会回到队首
//            channel.basicPublish(message.getMessageProperties().getReceivedExchange(),
//                    message.getMessageProperties().getReceivedRoutingKey(), MessageProperties.PERSISTENT_TEXT_PLAIN,
//                    str.getBytes(StandardCharsets.UTF_8));
        }
    }
}
