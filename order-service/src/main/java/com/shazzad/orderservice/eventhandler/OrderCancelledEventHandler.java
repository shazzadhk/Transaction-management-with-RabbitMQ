package com.shazzad.orderservice.eventhandler;

import com.shazzad.orderservice.event.OrderCancelledEvent;
import com.shazzad.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderCancelledEventHandler {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = {"${queue.order-canceled}"})
    public void onOrderCanceled(OrderCancelledEvent payload) {

        log.info("Handling a order cancelled event {}", payload);

//        OrderCancelledEvent event = converter.toObject(payload, OrderCancelledEvent.class);

        orderService.cancelOrder(payload.getOrder().getId());
    }
}
