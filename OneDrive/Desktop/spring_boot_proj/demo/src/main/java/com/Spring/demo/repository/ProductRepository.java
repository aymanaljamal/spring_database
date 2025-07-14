package com.Spring.demo.repository;

import com.Spring.demo.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCreatedById(Long employeeId);
    List<Product> findByNameContainingIgnoreCase(String name);
}
