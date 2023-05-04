package com.shazzad.inventoryservice.eventhandler;

import com.shazzad.inventoryservice.event.BilledOrderEvent;
import com.shazzad.inventoryservice.event.ResetInventoryEvent;
import com.shazzad.inventoryservice.exception.StockException;
import com.shazzad.inventoryservice.service.InventoryService;
import com.shazzad.inventoryservice.util.TransactionIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BilledOrderHandler {

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private TransactionIdHolder transactionIdHolder;

    @RabbitListener(queues = {"${queue.order-create}"})
    public void handle(BilledOrderEvent payload) {

        log.info("Handling a billed order event {}", payload);

        transactionIdHolder.setCurrentTransactionId(payload.getTransactionId());

        try {

            inventoryService.updateQuantity(payload.getOrder());

        } catch (StockException e) {

            log.error("Cannot update stock, reason: {}", e.getMessage());

        }
    }


    @RabbitListener(queues = {"${queue.reset-inventory}"})
    public void handleResetInventory(ResetInventoryEvent payload) {

        log.info("Handling a reset inventory event {}", payload);

        transactionIdHolder.setCurrentTransactionId(payload.getTransactionId());

        inventoryService.resetInventoryQuantity(payload.getOrder());

    }
}
