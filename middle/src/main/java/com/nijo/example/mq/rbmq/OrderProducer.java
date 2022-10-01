package com.nijo.example.mq.rbmq;

import com.rabbitmq.client.Return;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * 生产者
 * */
@RestController
public class OrderProducer implements RabbitTemplate.ConfirmCallback {
    /*注入RabbitTemplate*/
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void initRabbitTemplate() {
        //注册回调 this 实现ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        //队列投递失败退回信息
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            Message message = returnedMessage.getMessage();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            //重新发送
            rabbitTemplate.convertAndSend(exchange,routingKey,message);
        });
        //失败退回，可以在yml配置
        //rabbitTemplate.setMandatory(true);

    }

    @GetMapping("/addOrder")
    public String addOrder(){

        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟~~~",message -> {
            //60S未消费
            message.getMessageProperties().setExpiration("60000");
            message.getMessageProperties().setCorrelationId("1");
            message.getMessageProperties().setMessageId("1");

            return message;
        },new CorrelationData("1"));


        rabbitTemplate.convertAndSend("order_exchange","order.1","我是1分钟10秒~~~",message -> {
            //70S未消费
            message.getMessageProperties().setExpiration("70000");
            message.getMessageProperties().setMessageId("2");
            message.getMessageProperties().setCorrelationId("2");
            return message;
        },new CorrelationData("2"));


        rabbitTemplate.convertAndSend("order_exchange","order.1","我是10秒钟~~~",message -> {
            //10S未消费
            message.getMessageProperties().setExpiration("10000");
            message.getMessageProperties().setMessageId("3");
            message.getMessageProperties().setCorrelationId("3");
            return message;
        },new CorrelationData("3") );
        return "成功";
    }

    /**
     * 带插件的延时请求
     * */
    @GetMapping("/addDLXOrder")
    public String addDLXOrder(){
        //注册回调 this 实现ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend("dlx_order_exchange","dlxOrder","带插件延时，我是30秒~~~",message -> {
            //30S未消费
            message.getMessageProperties().setDelay(30000);
            message.getMessageProperties().setMessageId("1");
            return message;
        });

        rabbitTemplate.convertAndSend("dlx_order_exchange","dlxOrder","带插件延时，我是20秒~~~",message -> {
            //20S未消费
            message.getMessageProperties().setDelay(20000);
            message.getMessageProperties().setMessageId("2");
            return message;
        });
        rabbitTemplate.convertAndSend("dlx_order_exchange","dlxOrder","带插件延时，我是10秒钟~~~",message -> {
            //10S未消费
            message.getMessageProperties().setDelay(10000);
            message.getMessageProperties().setMessageId("3");
            return message;
        });
        return "成功";
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String exception) {
        System.out.println(correlationData);
        if(ack){
            System.out.println("消息投递成功");
        }else {
            System.out.println("消息投递失败原因:"+exception);
        }
    }
}
