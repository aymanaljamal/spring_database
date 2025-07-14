package com.Spring.demo.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String role;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "employee-products")
    private List<Product> products;

    @OneToMany(mappedBy = "approvedBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "employee-orders")
    private List<Order> approvedOrders;

    // Getters
    public Long getId() { return id; }
    public String getRole() { return role; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public List<Product> getProducts() { return products; }
    public List<Order> getApprovedOrders() { return approvedOrders; }

    // Setters
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }
    public void setName(String name) { this.name = name; }
    public void setId(Long id) { this.id = id; }
    public void setProducts(List<Product> products) { this.products = products; }
    public void setApprovedOrders(List<Order> approvedOrders) { this.approvedOrders = approvedOrders; }
}
