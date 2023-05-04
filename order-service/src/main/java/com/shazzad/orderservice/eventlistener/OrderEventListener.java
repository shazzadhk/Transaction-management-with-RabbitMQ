package com.shazzad.orderservice.eventlistener;

import com.shazzad.orderservice.event.OrderCreatedEvent;
import com.shazzad.orderservice.event.ResetInventoryEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@Slf4j
public class OrderEventListener {

    @Value("${exchange.order-exchange}")
    private String exchange;
    @Value("${routingKey.order-create-key}")
    private String orderCreateRoutingKey;

    @Value("${routingKey.reset-inventory-key}")
    private String resetInventoryRoutingKey;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${queue.order-create}")
    private String queueOrderCreateName;

    @Value("${queue.reset-inventory}")
    private String resetInventoryQueue;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(OrderCreatedEvent event) {

        log.info("Sending order created event to {}, event: {}", queueOrderCreateName, event);

        rabbitTemplate.convertAndSend(exchange,orderCreateRoutingKey, event);

    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_ROLLBACK)
    public void onResetInventoryEvent(ResetInventoryEvent event){

        log.info("sending reset inventory event to {}, event: {}",resetInventoryQueue,event);

        rabbitTemplate.convertAndSend(exchange,resetInventoryRoutingKey,event);
    }

}
