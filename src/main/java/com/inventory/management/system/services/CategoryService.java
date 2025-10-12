package com.inventory.management.system.services;

import com.inventory.management.system.dtos.CategoryRequestDto;
import com.inventory.management.system.dtos.ProductRequestDto;
import com.inventory.management.system.entities.Category;
import com.inventory.management.system.entities.Product;
import com.inventory.management.system.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    ModelMapper modelMapper = new ModelMapper();

    public Category createCategory(CategoryRequestDto categoryRequestDto) {
        Category category = modelMapper.map(categoryRequestDto, Category.class);
        return categoryRepository.save(category);
    }

    public Category getOneCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity with ID " + id + " not found"));
    }

    public Category updateCategory(Long id, CategoryRequestDto categoryRequestDto) {
        //find the existing category
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        modelMapper.map(categoryRequestDto, existing);
        // Save updated product
        return categoryRepository.save(existing);

    }

    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));

        categoryRepository.delete(category);
    }



}
