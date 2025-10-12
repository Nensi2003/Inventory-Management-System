package com.inventory.management.system.dtos;

import com.inventory.management.system.entities.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Category category;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
