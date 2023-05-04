package com.shazzad.paymentservice.eventlistener;

import com.shazzad.paymentservice.event.OrderCancelledEvent;
import com.shazzad.paymentservice.event.OrderDoneEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class PaymentEventListener {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${exchange.order-exchange}")
    private String exchange;

    @Value("${queue.order-done}")
    private String orderDoneQueue;

    @Value("${queue.order-canceled}")
    private String orderCancelledQueue;

    @Value("${routingKey.order-done-key}")
    private String orderDoneRoutingKey;

    @Value("${routingKey.order-canceled-key}")
    private String orderCancelledRoutingKey;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderDoneEvent(OrderDoneEvent event) {

        log.info("Sending order done event {}, event: {}", orderDoneQueue, event);

        rabbitTemplate.convertAndSend(exchange,orderDoneRoutingKey, event);

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onOrderCancelledEvent(OrderCancelledEvent event) {

        log.info("Sending order canceled event to {}, event: {}", orderCancelledQueue, event);

        rabbitTemplate.convertAndSend(exchange,orderCancelledRoutingKey, event);

    }
}
