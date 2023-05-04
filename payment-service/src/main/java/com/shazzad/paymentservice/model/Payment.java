package com.shazzad.paymentservice.model;

import com.shazzad.paymentservice.util.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Table(name = "payments")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "total_bill")
    private Float totalBilled;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;
}
