package com.Spring.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean approved = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference(value = "customer-orders")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_id")
    @JsonBackReference(value = "employee-orders")
    private Employee approvedBy;

    // Getters
    public Long getId() { return id; }
    public boolean isApproved() { return approved; }
    public Customer getCustomer() { return customer; }
    public Product getProduct() { return product; }
    public Employee getApprovedBy() { return approvedBy; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setApproved(boolean approved) { this.approved = approved; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public void setProduct(Product product) { this.product = product; }
    public void setApprovedBy(Employee approvedBy) { this.approvedBy = approvedBy; }
}
