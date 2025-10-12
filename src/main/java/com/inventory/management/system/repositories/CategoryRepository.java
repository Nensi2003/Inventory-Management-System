package com.inventory.management.system.repositories;

import com.inventory.management.system.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
