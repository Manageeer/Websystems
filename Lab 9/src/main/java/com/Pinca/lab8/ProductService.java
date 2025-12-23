package com.casaba.lab8;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    private Map<Long, Product> products = new HashMap<>();
    private Long nextId = 4L; // since 1,2,3 are preloaded

    public ProductService() {
        products.put(1L, new Product(1L, "Laptop Pro", 59999.0));
        products.put(2L, new Product(2L, "Gaming Mouse", 1299.0));
        products.put(3L, new Product(3L, "Mechanical Keyboard", 4599.0));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProductById(Long id) {
        return products.get(id);
    }

    public Product createProduct(Product product) {
        product.setId(nextId++);
        products.put(product.getId(), product);
        return product;
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        if (!products.containsKey(id)) {
            return null;
        }
        updatedProduct.setId(id);
        products.put(id, updatedProduct);
        return updatedProduct;
    }

    public boolean deleteProduct(Long id) {
        return products.remove(id) != null;
    }
}
