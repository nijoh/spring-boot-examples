package com.nijo.example.mq.rbmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//声明MQ队列、交换机
@Configuration
public class RabbitMqConfig {
    //订单队列交换机
    private static final String ORDER_EXCHANGE="order_exchange";
    //订单队列
    private static final String ORDER_QUEUE="order_queue";
    //路由key 带通配符 以order. 开头命中
    private static final String ORDER_ROUTINGKEY="order.#";


    //订单死信交换机
    private static final String DLX_ORDER_EXCHANGE="dlx_order_exchange";
    //订单死信对列
    private static final String DLX_ORDER_QUEUE="dlx_order_queue";
    //路由Key
    private static final String DLX_ROUTINGKEY="dlxOrder";

    //订单异常交换机
    private static final String EXCEPTION_ORDER_EXCHANGE="exception_order_exchange";
    //订单异常队列
    private static final String EXCEPTION_ORDER_QUEUE="exception_order_queue";
    //订单异常路由Key
    private static final String EXCEPTION_ROUTINGKEY="exceptionOrder";




    //订单交换机
    @Bean(RabbitMqConfig.ORDER_EXCHANGE)
    public TopicExchange orderExchange(){
        //durable持久化交换机 topic模式
        return ExchangeBuilder.topicExchange(RabbitMqConfig.ORDER_EXCHANGE).durable(true).build();
    }

    //订单队列
    @Bean(RabbitMqConfig.ORDER_QUEUE)
    public Queue orderQueue(){
        //持久化队列 绑定死信交换机和死信路由Key
        return  QueueBuilder.durable(RabbitMqConfig.ORDER_QUEUE)
                .withArgument("x-dead-letter-exchange",RabbitMqConfig.DLX_ORDER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",RabbitMqConfig.DLX_ROUTINGKEY).build();
    }

    //订单与交换绑定
    @Bean
    public Binding bindingOrderAndExchange(@Qualifier(RabbitMqConfig.ORDER_QUEUE)Queue orderQueue,
                                           @Qualifier(RabbitMqConfig.ORDER_EXCHANGE)TopicExchange orderExchange ){
        //队列 与 交换机 绑定 topic key order.开头的命中
        return BindingBuilder.bind(orderQueue).to(orderExchange).with(RabbitMqConfig.ORDER_ROUTINGKEY);
    }


    //死信订单交换机
    @Bean(RabbitMqConfig.DLX_ORDER_EXCHANGE)
    public DirectExchange dlxOrderExchange(){
        //durable持久化交换机 direct模式
        return ExchangeBuilder.directExchange(RabbitMqConfig.DLX_ORDER_EXCHANGE).durable(true).build();
    }

    //死信订单队列
    @Bean(RabbitMqConfig.DLX_ORDER_QUEUE)
    public Queue dlxOrderQueue(){
        //持久化队列
        return  QueueBuilder.durable(RabbitMqConfig.DLX_ORDER_QUEUE).build();
    }

    //死信订单队列与交换绑定
    @Bean
    public Binding bindingDlxOrderAndExchange(@Qualifier(RabbitMqConfig.DLX_ORDER_QUEUE)Queue dlxOrderQueue,
                                           @Qualifier(RabbitMqConfig.DLX_ORDER_EXCHANGE)DirectExchange dlxOrderExchange ){
        //队列 与 交换机 绑定 topic key order.开头的命中
        return BindingBuilder.bind(dlxOrderQueue).to(dlxOrderExchange).with(RabbitMqConfig.DLX_ROUTINGKEY);
    }


    //异常订单交换机
    @Bean(RabbitMqConfig.EXCEPTION_ORDER_EXCHANGE)
    public DirectExchange exceptionOrderExchange(){
        //durable持久化交换机 direct模式
        return ExchangeBuilder.directExchange(RabbitMqConfig.EXCEPTION_ORDER_EXCHANGE).durable(true).build();
    }

    //异常订单队列
    @Bean(RabbitMqConfig.EXCEPTION_ORDER_QUEUE)
    public Queue exceptionOrderQueue(){
        //持久化队列
        return  QueueBuilder.durable(RabbitMqConfig.EXCEPTION_ORDER_QUEUE).build();
    }

    //异常订单队列与交换绑定
    @Bean
    public Binding bindingExceptionOrderAndExchange(@Qualifier(RabbitMqConfig.EXCEPTION_ORDER_QUEUE)Queue exceptionOrderQueue,
                                              @Qualifier(RabbitMqConfig.EXCEPTION_ORDER_EXCHANGE)DirectExchange exceptionOrderExchange ){
        //队列 与 交换机 绑定 topic key order.开头的命中
        return BindingBuilder.bind(exceptionOrderQueue).to(exceptionOrderExchange).with(RabbitMqConfig.EXCEPTION_ROUTINGKEY);
    }
}
