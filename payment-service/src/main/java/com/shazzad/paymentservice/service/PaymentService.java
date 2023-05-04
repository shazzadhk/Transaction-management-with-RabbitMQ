package com.shazzad.paymentservice.service;

import com.shazzad.paymentservice.dto.Order;
import com.shazzad.paymentservice.event.OrderCancelledEvent;
import com.shazzad.paymentservice.event.OrderDoneEvent;
import com.shazzad.paymentservice.model.Account;
import com.shazzad.paymentservice.model.Payment;
import com.shazzad.paymentservice.repository.AccountRepository;
import com.shazzad.paymentservice.repository.PaymentRepository;
import com.shazzad.paymentservice.util.PaymentStatus;
import com.shazzad.paymentservice.util.TransactionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TransactionHandler transactionIdHolder;

    @Transactional
    public void charge(Order order) {


        log.info("Charging order {}", order);

        Payment payment = createPayment(order);

        log.info("Saving payment {}", payment);

        paymentRepository.save(payment);

        if(confirmCharge(order)){
            publishOrderDone(order);
        }else{
            publishCanceledOrder(order);
        }


    }

    private boolean confirmCharge(Order order) {

        log.info("Confirm charge for order id {}", order.getId());

        Account account = accountService.getAccountByUser(order.getUserId());

        return account.getBalance() >= order.getValue();

    }

//    private void publish(Order order) {
//
//        BilledOrderEvent billedOrderEvent = new BilledOrderEvent(transactionIdHolder.getCurrentTransactionId(), order);
//
//        log.debug("Publishing a billed order event {}", billedOrderEvent);
//
//        publisher.publishEvent(billedOrderEvent);
//
//    }

    private Payment createPayment(Order order) {

        return Payment.builder()
                .paymentStatus(PaymentStatus.BILLED)
                .totalBilled(order.getValue())
                .orderId(order.getId())
                .build();

    }


    private void publishCanceledOrder(Order order) {

        refund(order.getId());

        OrderCancelledEvent event = new OrderCancelledEvent(transactionIdHolder.getCurrentTransactionId(), order);

        log.info("Publishing canceled order event {}", event);

        publisher.publishEvent(event);

    }

    private void publishOrderDone(Order order) {

        withdraw(order);

        OrderDoneEvent event = new OrderDoneEvent(transactionIdHolder.getCurrentTransactionId(), order);

        log.info("Publishing order done event {}", event);

        publisher.publishEvent(event);

    }

    public void refund(Long orderId) {

        log.info("Refund Payment by order id {}", orderId);

        Optional<Payment> paymentOptional = paymentRepository.findByOrderId(orderId);

        if (paymentOptional.isPresent()) {

            Payment payment = paymentOptional.get();
            payment.setPaymentStatus(PaymentStatus.REFUND);
            paymentRepository.save(payment);

            log.info("Payment {} was refund", payment.getId());

        } else {

            log.error("Cannot find the Payment by order id {} to refund", orderId);

        }
    }

    public void withdraw(Order order){

        log.info("withdraw money from user account");

        Account account = accountService.getAccountByUser(order.getUserId());

        account.setBalance(account.getBalance() - order.getValue());

        log.info("after withdraw user balance: {}",account.getBalance());

        accountRepository.save(account);
    }
}
