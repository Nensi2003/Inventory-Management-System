package com.inventory.management.system.services;

import com.inventory.management.system.dtos.ProductRequestDto;
import com.inventory.management.system.dtos.ProductResponseDto;
import com.inventory.management.system.entities.Category;
import com.inventory.management.system.entities.Product;
import com.inventory.management.system.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    public Product createProduct(ProductRequestDto productRequestDto) {
        //DTO -> Entitet
        Product product = modelMapper.map(productRequestDto, Product.class);
        product.setCategory(new Category(productRequestDto.getCategoryId()));
        product.setId(null);
        return productRepository.save(product);
    }


    //Nga cila property do front endi te beje order
    private boolean isValidSortFields(String fieldName) {
        return Arrays.stream(Product.class.getDeclaredFields())
                .anyMatch(field -> field.getName().equals(fieldName));
    }

    public Page<ProductResponseDto> getAllProducts(Integer page, Integer size, String direction,
                                                   String sortBy, Long categoryId) {
        if (!this.isValidSortFields(sortBy)) {
            sortBy = "createdAt";
        }
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Product> productPage;
        if (categoryId != null) {
            productPage = this.productRepository
                    .findByCategory(new Category(categoryId), pageable);
        } else {
            productPage = this.productRepository.findAll(pageable);
        }

        return productPage.map(product ->
                modelMapper.map(product, ProductResponseDto.class));
    }


    public Product updateProduct(Long id, ProductRequestDto productRequestDto) {
        //find the existing product
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(productRequestDto, existing);
        // Save updated product
        return productRepository.save(existing);

    }

    public Product getOneProduct(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found"));
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        productRepository.delete(product);
    }



    // Scheduled check every day at 9 AM
    @Scheduled(cron = "0 0 9 * * ?")
    public void checkLowStockScheduled() {
        List<Product> lowStockProducts = productRepository.findByQuantityLessThan(5);
        if (!lowStockProducts.isEmpty()) {
            // For now just log them (later you could email/notify)
            lowStockProducts.forEach(product ->
                    System.out.println("⚠ Low stock alert: " + product.getName()
                            + " has " + product.getQuantity() + " items left")
            );
        }
    }

    public List<ProductResponseDto> getLowStockProducts(Integer limit) {
        List<Product> products = productRepository.findByQuantityLessThan(limit);
        return products.stream()
                .map(product ->
                        modelMapper.map(product, ProductResponseDto.class))
                .toList();
    }
}
