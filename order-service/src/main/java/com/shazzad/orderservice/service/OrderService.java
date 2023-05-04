package com.shazzad.orderservice.service;

import com.shazzad.orderservice.event.OrderCreatedEvent;
import com.shazzad.orderservice.event.ResetInventoryEvent;
import com.shazzad.orderservice.model.Order;
import com.shazzad.orderservice.repository.OrderRepository;
import com.shazzad.orderservice.util.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository repository;
    @Autowired
    private ApplicationEventPublisher publisher;

    @Transactional
    public Order createOrder(Order order) {

        order.setStatus(OrderStatus.NEW);

        log.info("Saving an order {}", order);

        Order returnOrder = repository.save(order);

        publish(order);

        return returnOrder;

    }

    private void publish(Order order) {

        OrderCreatedEvent event = new OrderCreatedEvent(UUID.randomUUID().toString(), order);

        log.info("Publishing an order created event {}", event);

        publisher.publishEvent(event);

    }

    public List<Order> findAll() {

        return repository.findAll();
    }

    @Transactional
    public void updateOrderAsDone(Long orderId) {

        log.debug("Updating Order {} to {}", orderId, OrderStatus.DONE);

        Optional<Order> orderOptional = repository.findById(orderId);

        if (orderOptional.isPresent()) {

            Order order = orderOptional.get();
            order.setStatus(OrderStatus.DONE);
            repository.save(order);

            log.debug("Order {} done", order.getId());

        } else {

            log.error("Cannot update Order to status {}, Order {} not found", OrderStatus.DONE, orderId);

        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {

        log.info("Canceling Order {}", orderId);

        Optional<Order> optionalOrder = repository.findById(orderId);

        if (optionalOrder.isPresent()) {

            Order order = optionalOrder.get();
            order.setStatus(OrderStatus.CANCELED);
            repository.save(order);

            publishResetInventoryEvent(order);

            log.info("Order {} was canceled", order.getId());

        } else {

            log.error("Cannot find an Order {}", orderId);

        }
    }

    public void publishResetInventoryEvent(Order order) {

        ResetInventoryEvent event = new ResetInventoryEvent(UUID.randomUUID().toString(), order);

        log.info("Publishing an reset inventory event {}", event);

        publisher.publishEvent(event);

    }
}
