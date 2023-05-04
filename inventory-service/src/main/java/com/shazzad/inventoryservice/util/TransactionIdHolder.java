package com.shazzad.inventoryservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionIdHolder {

    private final ThreadLocal<String> currentTransactionId = new ThreadLocal<>();

    public String getCurrentTransactionId() {

        log.info("current transaction: {}",currentTransactionId);
        return currentTransactionId.get();

    }

    public void setCurrentTransactionId(String transactionId) {

        log.info("setting current transaction:{}",transactionId);
        currentTransactionId.set(transactionId);

    }
}
