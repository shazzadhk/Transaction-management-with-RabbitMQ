package com.shazzad.orderservice.event;

import com.shazzad.orderservice.model.Order;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderCreatedEvent {

    private String transactionId;

    private Order order;
}
