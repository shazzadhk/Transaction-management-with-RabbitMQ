package com.shazzad.paymentservice.event;

import com.shazzad.paymentservice.dto.Order;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RefundPaymentEvent {

    private String transactionId;

    private Order order;
}
