package com.inventory.management.system.dtos;

import lombok.Data;

@Data
public class OrderRequestDto {
    Long customerId;
    Long productId;
    Integer quantity;
}
