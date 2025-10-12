package com.inventory.management.system.repositories;

import com.inventory.management.system.entities.Category;
import com.inventory.management.system.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    //produkti mund te gjendet ose jo ne DB
    Optional<Product> findByName(String name);
    Page<Product> findByCategory(Category category, Pageable pageable);
    List<Product> findByQuantityLessThan(Integer quantity);

}
