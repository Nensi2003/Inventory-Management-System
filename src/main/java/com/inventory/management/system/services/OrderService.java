package com.inventory.management.system.services;

import com.inventory.management.system.dtos.OrderRequestDto;
import com.inventory.management.system.dtos.OrderResponseDto;
import com.inventory.management.system.entities.Order;
import com.inventory.management.system.entities.Product;
import com.inventory.management.system.repositories.OrderRepository;
import com.inventory.management.system.enums.OrderStatus;
import com.inventory.management.system.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    ModelMapper modelMapper = new ModelMapper();

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Product product = productRepository.findById(orderRequestDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Order order = new Order();
        order.setCustomerId(orderRequestDto.getCustomerId());
        order.setProduct(product);
        order.setQuantity(orderRequestDto.getQuantity());
        order.setTotal(product.getPrice() * orderRequestDto.getQuantity());

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderResponseDto.class);
    }

    // Update status
    public OrderResponseDto updateOrderStatus(Long id, OrderStatus status) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);

        return modelMapper.map(updatedOrder, OrderResponseDto.class);
    }
}
