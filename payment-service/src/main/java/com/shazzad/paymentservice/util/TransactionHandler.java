package com.shazzad.paymentservice.util;

import org.springframework.stereotype.Component;

@Component
public class TransactionHandler {

    private final ThreadLocal<String> currentTransactionId = new ThreadLocal<>();

    public String getCurrentTransactionId() {

        return currentTransactionId.get();

    }

    public void setCurrentTransactionId(String transactionId) {

        currentTransactionId.set(transactionId);

    }
}
