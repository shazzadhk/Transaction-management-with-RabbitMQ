package com.shazzad.orderservice.model;

import com.shazzad.orderservice.util.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_tbl")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity_num")
    private Long quantityNum;

    @Column(name = "value")
    private Float value;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

}
