package com.shazzad.inventoryservice.event;

import com.shazzad.inventoryservice.dto.Order;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResetInventoryEvent {

    private String transactionId;

    private Order order;
}
