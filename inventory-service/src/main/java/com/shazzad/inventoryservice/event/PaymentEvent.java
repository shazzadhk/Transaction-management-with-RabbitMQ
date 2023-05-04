package com.shazzad.inventoryservice.event;

import com.shazzad.inventoryservice.dto.Order;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentEvent {

    private String transactionId;
    private Order order;
}
