package com.shazzad.orderservice.eventhandler;

import com.shazzad.orderservice.event.OrderDoneEvent;
import com.shazzad.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderDoneEventHandler {

    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = {"${queue.order-done}"})
    public void handleOrderDoneEvent(OrderDoneEvent orderDoneEvent) {

        log.info("Handling a order done event {}", orderDoneEvent);

//        OrderDoneEvent event = converter.toObject(payload, OrderDoneEvent.class);

        orderService.updateOrderAsDone(orderDoneEvent.getOrder().getId());

    }


}
