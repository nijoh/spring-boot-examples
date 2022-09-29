package com.nijo.example.mq.rbmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者
 * */
@RestController
public class OrderProducer {
    /*注入RabbitTemplate*/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/addOrder")
    public String addOrder(){
        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟~~~",message -> {
            //60S未消费
            message.getMessageProperties().setExpiration("60000");
            message.getMessageProperties().setMessageId("1");
            return message;
        });

        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟10秒~~~",message -> {
            //70S未消费
            message.getMessageProperties().setExpiration("70000");
            message.getMessageProperties().setMessageId("2");
            return message;
        });
        rabbitTemplate.convertAndSend("order_exchange","order.1","我是10秒钟~~~",message -> {
            //10S未消费
            message.getMessageProperties().setExpiration("10000");
            message.getMessageProperties().setMessageId("3");
            return message;
        });
        return "成功";
    }
}
