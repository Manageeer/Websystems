package com.casaba.lab8.controller;

import com.casaba.lab8.dto.ProductInput;
import com.casaba.lab8.entity.Product;
import com.casaba.lab8.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody ProductInput input) {
        // Convert DTO to Entity
        Product product = new Product();
        product.setName(input.getName());
        product.setPrice(input.getPrice());
        product.setDescription(input.getDescription());
        product.setQuantity(input.getQuantity());

        // FIXED: Call createProduct() instead of saveProduct()
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductInput input) {

        // Convert DTO to Entity
        Product productUpdates = new Product();
        productUpdates.setName(input.getName());
        productUpdates.setPrice(input.getPrice());
        productUpdates.setDescription(input.getDescription());
        productUpdates.setQuantity(input.getQuantity());

        try {
            Product updatedProduct = productService.updateProduct(id, productUpdates);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}