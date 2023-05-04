package com.shazzad.inventoryservice.eventlistener;

import com.shazzad.inventoryservice.event.OrderCalceledEvent;
import com.shazzad.inventoryservice.event.PaymentEvent;
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
public class InventoryEventListener {

    @Value("${exchange.order-exchange}")
    private String exchange;
    @Value("${routingKey.payment-create-key}")
    private String paymentCreateRoutingKey;

    @Value("${routingKey.order-canceled-key}")
    private String orderCanceledRoutingKey;

    @Value("${queue.payment-create}")
    private String queuePaymentName;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPaymentEvent(PaymentEvent event) {

        log.info("Sending payment event to {}, event: {}", queuePaymentName, event);

        rabbitTemplate.convertAndSend(exchange,paymentCreateRoutingKey, event);

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onOrderCancelledEvent(OrderCalceledEvent event) {

        log.info("Sending order canceled event to {}, event: {}", exchange, event);

        rabbitTemplate.convertAndSend(exchange, orderCanceledRoutingKey, event);

    }
}
