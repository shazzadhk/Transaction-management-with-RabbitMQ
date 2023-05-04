package com.shazzad.inventoryservice.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {

    private Long id;

    private Long userId;

    private Long productId;

    private Long quantityNum;

    private Float value;
}
