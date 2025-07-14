package com.Spring.demo.service;

import com.Spring.demo.model.Customer;
import com.Spring.demo.model.Order;
import com.Spring.demo.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Page<Customer> getAllCustomers(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return repo.findById(id);
    }

    public Customer createCustomer(Customer customer) {
        return repo.save(customer);
    }

    public Customer updateCustomer(Long id, Customer updated) {
        return repo.findById(id).map(c -> {
            c.setName(updated.getName());
            c.setEmail(updated.getEmail());
            c.setPhone(updated.getPhone());
            return repo.save(c);
        }).orElse(null);
    }

    public void deleteCustomer(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }

    public List<Order> getCustomerOrders(Long customerId) {
        Optional<Customer> customer = repo.findById(customerId);
        return customer.map(Customer::getOrders).orElse(Collections.emptyList());
    }
}
