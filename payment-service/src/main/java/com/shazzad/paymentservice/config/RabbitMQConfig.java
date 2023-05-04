package com.shazzad.paymentservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${queue.order-canceled}")
    private String orderCanceledQueue;

    @Value("${queue.payment-create}")
    private String paymentCreateQueue;

    @Value("${queue.order-done}")
    private String orderDoneQueue;

    @Value("${exchange.order-exchange}")
    private String exchange;

//    @Value("${routingKey.bill-create-key}")
//    private String billCreateRoutingKey;

    @Value("${routingKey.order-done-key}")
    private String orderDoneRoutingKey;

    @Value("${routingKey.order-canceled-key}")
    private String orderCanceledRoutingKey;

//     spring bean for rabbitmq queue
    @Bean
    public Queue orderDoneQueue(){
        return new Queue(orderDoneQueue);
    }

    @Bean
    public Queue paymentCreateQueue(){
        return new Queue(paymentCreateQueue);
    }

    @Bean
    public Queue orderCanceledQueue(){
        return new Queue(orderCanceledQueue);
    }



    // spring bean for rabbitmq exchange
    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    // binding between queue and exchange using routing key
    @Bean
    public Binding orderDoneBinding(){
        return BindingBuilder
                .bind(orderDoneQueue())
                .to(exchange())
                .with(orderDoneRoutingKey);
    }

    // binding between json queue and exchange using routing key
//    @Bean
//    public Binding paymentCreateBinding(){
//        return BindingBuilder
//                .bind(paymentCreateQueue())
//                .to(exchange())
//                .with();
//    }

    @Bean
    public Binding orderCanceledBinding(){
        return BindingBuilder
                .bind(orderCanceledQueue())
                .to(exchange())
                .with(orderCanceledRoutingKey);
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
