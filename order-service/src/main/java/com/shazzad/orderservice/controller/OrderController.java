package com.shazzad.orderservice.controller;

import com.shazzad.orderservice.model.Order;
import com.shazzad.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody Order order) {

        log.debug("Creating a new {}", order);

        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));

    }

    @GetMapping
    public ResponseEntity<List<Order>> getAll() {

        return ResponseEntity.ok().body(orderService.findAll());

    }
}
