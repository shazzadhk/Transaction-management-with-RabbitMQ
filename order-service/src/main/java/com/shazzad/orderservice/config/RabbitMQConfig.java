package com.shazzad.orderservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class RabbitMQConfig {

    @Value("${queue.order-create}")
    private String orderCreateQueue;

    @Value("${queue.order-done}")
    private String orderDoneQueue;

    @Value("${queue.order-canceled}")
    private String orderCancelQueue;

    @Value("${queue.reset-inventory}")
    private String resetInventoryQueue;

    @Value("${exchange.order-exchange}")
    private String exchange;

    @Value("${routingKey.order-create-key}")
    private String orderCreateRoutingKey;

    @Value("${routingKey.order-done-key}")
    private String orderDoneRoutingKey;

    @Value("${routingKey.order-canceled-key}")
    private String orderCanceledRoutingKey;

    @Value("${routingKey.reset-inventory-key}")
    private String resetInventoryRoutingKey;

    // spring bean for rabbitmq queue
    @Bean
    public Queue orderCreateQueue(){
        //How much time a message lived in a queue
        Map<String,Object> args = new HashMap<>();
        args.put("x-message-ttl",60000);
        return new Queue(orderCreateQueue,true,false,false,args);
    }

    @Bean
    public Queue orderDoneQueue(){
        return new Queue(orderDoneQueue);
    }

    @Bean
    public Queue orderCanceledQueue(){
        return new Queue(orderCancelQueue);
    }

    @Bean
    public Queue resetInventoryQueue(){
        return new Queue(resetInventoryQueue);
    }

    // spring bean for rabbitmq exchange
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding orderCreateBinding(){
        return BindingBuilder
                .bind(orderCreateQueue())
                .to(exchange())
                .with(orderCreateRoutingKey);
    }

    // binding between json queue and exchange using routing key
    @Bean
    public Binding orderDoneBinding(){
        return BindingBuilder
                .bind(orderDoneQueue())
                .to(exchange())
                .with(orderDoneRoutingKey);
    }

    @Bean
    public Binding orderCanceledBinding(){
        return BindingBuilder
                .bind(orderCanceledQueue())
                .to(exchange())
                .with(orderCanceledRoutingKey);
    }

    @Bean
    public Binding resetInventoryBinding(){
        return BindingBuilder
                .bind(resetInventoryQueue())
                .to(exchange())
                .with(resetInventoryRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
