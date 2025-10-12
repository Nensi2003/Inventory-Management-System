package com.inventory.management.system.dtos;

//inputet qe kerkojme ne nga perdoruesi

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private Integer quantity;
    private Long categoryId;
}
