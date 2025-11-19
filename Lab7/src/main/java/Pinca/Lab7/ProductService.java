package Pinca.Lab7;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ProductService {

    private Map<Long, Product> productDB = new HashMap<>();
    private long nextId = 4;

    public ProductService() {
        productDB.put(1L, new Product(1L, "Laptop Pro", 50000.0));
        productDB.put(2L, new Product(2L, "Gaming Mouse", 1500.0));
        productDB.put(3L, new Product(3L, "Mechanical Keyboard", 3500.0));
    }

    public List<Product> getAll() {
        return new ArrayList<>(productDB.values());
    }

    public Product getById(Long id) {
        return productDB.get(id);
    }

    public Product create(Product product) {
        product.setId(nextId++);
        productDB.put(product.getId(), product);
        return product;
    }

    public Product update(Long id, Product updated) {
        if (!productDB.containsKey(id)) return null;
        updated.setId(id);
        productDB.put(id, updated);
        return updated;
    }

    public boolean delete(Long id) {
        return productDB.remove(id) != null;
    }
}
