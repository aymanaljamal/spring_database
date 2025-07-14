package com.Spring.demo.controller;

import com.Spring.demo.model.Employee;
import com.Spring.demo.model.Product;
import com.Spring.demo.service.EmployeeService;
import com.Spring.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final EmployeeService employeeService;
    public ProductController(ProductService productService, EmployeeService employeeService) {
        this.productService = productService;
        this.employeeService = employeeService;
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getOne(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Map<String, Object> data) {
        try {
            String name = (String) data.get("name");
            String description = (String) data.get("description");
            Double price = Double.valueOf(data.get("price").toString());
            Long employeeId = Long.valueOf(data.get("employeeId").toString());

            Optional<Employee> emp = employeeService.getEmployeeById(employeeId);
            if (emp.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setCreatedBy(emp.get());

            return ResponseEntity.status(201).body(productService.createProduct(product));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        Product updated = productService.updateProduct(id, product);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByName(name));
    }

}
