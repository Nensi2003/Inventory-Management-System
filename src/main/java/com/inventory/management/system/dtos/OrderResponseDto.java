package com.inventory.management.system.dtos;

import com.inventory.management.system.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDto {
    private Long id;
    private Long customerId;
    private Long productId;
    private Integer quantity;
    private Double total;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OrderStatus status;
}
