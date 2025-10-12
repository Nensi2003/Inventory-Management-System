package com.inventory.management.system.controllers;

import com.inventory.management.system.dtos.ProductRequestDto;
import com.inventory.management.system.dtos.ProductResponseDto;
import com.inventory.management.system.entities.Product;
import com.inventory.management.system.repositories.ProductRepository;
import com.inventory.management.system.services.ProductService;
import jakarta.persistence.Id;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private ModelMapper modelMapper = new ModelMapper();

    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct
            (@RequestBody ProductRequestDto productRequestDto) {
        Product product = productService.createProduct(productRequestDto);
        ProductResponseDto productResponseDto =
                modelMapper.map(product, ProductResponseDto.class);

        //responsi 201
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long id){
      Product product = this.productService.getOneProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                           @RequestBody ProductRequestDto productRequestDto){
       Product product = productService.updateProduct(id,productRequestDto);
       return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    //Generic API
    @GetMapping("/getAllProducts")
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            //Offset Values
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "20", required = false) Integer size,
            @RequestParam(name = "direction", defaultValue = "DESC", required = false) String direction,
            @RequestParam(name = "sort_by", defaultValue = "createdAt", required = false) String sortBy,
            @RequestParam(name = "category_id", required = false) Long categoryId
    )
    {

        return new ResponseEntity<>(productService.getAllProducts
                (page, size, direction, sortBy, categoryId) ,HttpStatus.OK);

    }


    @GetMapping("/notify_low_stock")
    public ResponseEntity<List<ProductResponseDto>> notifyLowStock(
            @RequestParam(name = "low_stock", defaultValue = "5") Integer lowStockLimit) {
        List<ProductResponseDto> lowStockProducts = productService.getLowStockProducts(lowStockLimit);
        return ResponseEntity.ok(lowStockProducts);
    }
}
