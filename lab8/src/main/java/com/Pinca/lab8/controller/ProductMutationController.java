package com.casaba.lab8;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.springframework.stereotype.Component;

@Component
public class ProductMutationController implements GraphQLMutationResolver {

    private final ProductService productService;

    public ProductMutationController(ProductService productService) {
        this.productService = productService;
    }

    public Product createProduct(ProductInput product) {
        Product newProduct = new Product();
        newProduct.setName(product.getName());
        newProduct.setPrice(product.getPrice());
        return productService.createProduct(newProduct);
    }

    public Product updateProduct(Long id, ProductInput product) {
        Product updated = new Product();
        updated.setName(product.getName());
        updated.setPrice(product.getPrice());
        return productService.updateProduct(id, updated);
    }

    public boolean deleteProduct(Long id) {
        return productService.deleteProduct(id);
    }
}
