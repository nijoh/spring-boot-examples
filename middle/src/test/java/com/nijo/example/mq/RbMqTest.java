package com.nijo.example.mq;

import com.nijo.example.mq.rbmq.RabbitMqConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RbMqTest {
    /*注入RabbitTemplate*/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSend(){
        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟~~~",message -> {
            //60S未消费
            message.getMessageProperties().setExpiration("60000");
            return message;
        });

        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟10秒~~~",message -> {
            //70S未消费
            message.getMessageProperties().setExpiration("70000");
            return message;
        });
        rabbitTemplate.convertAndSend("order_exchange","order.1","我是10秒钟~~~",message -> {
            //10S未消费
            message.getMessageProperties().setExpiration("10000");
            return message;
        });
    }
}
