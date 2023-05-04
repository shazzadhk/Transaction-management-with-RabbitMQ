package com.shazzad.orderservice.event;

import com.shazzad.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetInventoryEvent {

    private String transactionId;

    private Order order;
}
