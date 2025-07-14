package com.Spring.demo.service;
import com.Spring.demo.model.Product;
import com.Spring.demo.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Service
public class ProductService {
    private final ProductRepository repo;
    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }
    public List<Product> getAllProducts() {
        return repo.findAll();
    }
    public Optional<Product> getProductById(Long id) {
        return repo.findById(id);
    }
    public Product createProduct(Product product) {
        return repo.save(product);
    }
    public Product updateProduct(Long id, Product updated) {
        return repo.findById(id).map(p -> {
            p.setName(updated.getName());
            p.setDescription(updated.getDescription());
            p.setPrice(updated.getPrice());
            return repo.save(p);
        }).orElse(null);
    }
    public void deleteProduct(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
    public List<Product> getProductsByEmployeeId(Long employeeId) {
        return repo.findByCreatedById(employeeId);
    }
    public List<Product> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

}
