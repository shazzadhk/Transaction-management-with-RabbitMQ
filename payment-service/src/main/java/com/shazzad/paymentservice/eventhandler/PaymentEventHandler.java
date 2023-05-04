package com.shazzad.paymentservice.eventhandler;

import com.shazzad.paymentservice.event.PaymentCreateEvent;
import com.shazzad.paymentservice.exception.ChargeException;
import com.shazzad.paymentservice.service.PaymentService;
import com.shazzad.paymentservice.util.TransactionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventHandler {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private TransactionHandler transactionIdHolder;

    @RabbitListener(queues = {"${queue.payment-create}"})
    public void handle(PaymentCreateEvent payload) {

        log.info("Handling a payment event {}", payload);

        transactionIdHolder.setCurrentTransactionId(payload.getTransactionId());

//            inventoryService.updateQuantity(payload.getOrder());
            paymentService.charge(payload.getOrder());
    }
}
