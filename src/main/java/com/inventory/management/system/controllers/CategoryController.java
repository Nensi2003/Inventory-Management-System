package com.inventory.management.system.controllers;

import com.inventory.management.system.dtos.CategoryRequestDto;
import com.inventory.management.system.dtos.CategoryResponseDto;
import com.inventory.management.system.dtos.ProductRequestDto;
import com.inventory.management.system.dtos.ProductResponseDto;
import com.inventory.management.system.entities.Category;
import com.inventory.management.system.entities.Product;
import com.inventory.management.system.services.CategoryService;
import com.inventory.management.system.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> createCategory
            (@RequestBody CategoryRequestDto categoryRequestDto) {
        Category category = categoryService.createCategory(categoryRequestDto);
        CategoryResponseDto categoryResponseDto =
                modelMapper.map(category, CategoryResponseDto.class);

        return new ResponseEntity<>(categoryResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable("id") Long id){
        Category category = this.categoryService.getOneCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable("id") Long id,
                                                 @RequestBody CategoryRequestDto categoryRequestDto){
        Category category = categoryService.updateCategory(id,categoryRequestDto);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

//
//    @GetMapping("/getAllCategories")
//    public ResponseEntity<Page<CategoryResponseDto>> getAllCategories(
//            //Offset Values
//            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
//            @RequestParam(name = "size", defaultValue = "20", required = false) Integer size,
//            @RequestParam(name = "direction", defaultValue = "DESC", required = false) String direction,
//            @RequestParam(name = "sort_by", defaultValue = "createdAt", required = false) String sortBy,
//            @RequestParam(name = "category_id", required = false) Long categoryId
//    )
//    {
//
//        return new ResponseEntity<>(categoryService.getAllCategories
//                (page, size, direction, sortBy, categoryId) ,HttpStatus.OK);
//
//    }
}
