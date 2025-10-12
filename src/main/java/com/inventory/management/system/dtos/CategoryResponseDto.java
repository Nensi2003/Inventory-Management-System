package com.inventory.management.system.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryResponseDto {
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
