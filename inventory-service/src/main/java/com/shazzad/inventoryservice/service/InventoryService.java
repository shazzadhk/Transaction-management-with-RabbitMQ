package com.shazzad.inventoryservice.service;

import com.shazzad.inventoryservice.dto.Order;
import com.shazzad.inventoryservice.event.OrderCalceledEvent;
import com.shazzad.inventoryservice.event.PaymentEvent;
import com.shazzad.inventoryservice.exception.StockException;
import com.shazzad.inventoryservice.model.Product;
import com.shazzad.inventoryservice.repository.ProductRepository;
import com.shazzad.inventoryservice.util.TransactionIdHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private TransactionIdHolder transactionIdHolder;

    @Transactional
    public void updateQuantity(Order order) {

        log.info("Start updating product {}", order.getProductId());

        Product product = getProduct(order);

        checkStock(order, product);

        updateStock(order, product);

        publishCreatePayment(order);

    }

    private void updateStock(Order order, Product product) {

        product.setQuantity(product.getQuantity() - order.getQuantityNum());

        log.info("Updating product {} with quantity {}", product.getId(), product.getQuantity());

        productRepository.save(product);

    }

    private void publishCreatePayment(Order order) {

        PaymentEvent event = new PaymentEvent(transactionIdHolder.getCurrentTransactionId(), order);

        log.info("Publishing create payment event {}", event);

        publisher.publishEvent(event);

    }

    private void checkStock(Order order, Product product) {

        log.info("Checking, products available {}, products ordered {}", product.getQuantity(), order.getQuantityNum());

        if (product.getQuantity() < order.getQuantityNum()) {

            publishCanceledOrder(order);

            throw new StockException("Product " + product.getId() + " is out of stock");

        }
    }

    private void publishCanceledOrder(Order order) {

        OrderCalceledEvent event = new OrderCalceledEvent(transactionIdHolder.getCurrentTransactionId(), order);

        log.debug("Publishing canceled order event {}", event);

        publisher.publishEvent(event);

    }

    private Product getProduct(Order order) {

        Optional<Product> optionalProduct = productRepository.findById(order.getProductId());

        return optionalProduct.orElseThrow(() -> {

            publishCanceledOrder(order);

            return new StockException("Cannot find a product " + order.getProductId());

        });
    }

    @Transactional
    public void resetInventoryQuantity(Order order){

        log.info("start to reset product quantity for productId {}",order.getProductId());

        Product product = productRepository.findById(order.getProductId()).get();

        log.info("product info {}",product);

        product.setQuantity(product.getQuantity() + order.getQuantityNum());

        log.info("after reset product quantity {}",product.getQuantity());

        productRepository.save(product);
    }
}
