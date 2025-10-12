package com.inventory.management.system.entities;

import com.inventory.management.system.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name="orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long customerId;
    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;
    Integer quantity;
    Double total;
    LocalDateTime createAt;
    LocalDateTime updateAt;




    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.NEW;
}
