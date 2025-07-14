package com.Spring.demo.controller;

import com.Spring.demo.model.Customer;
import com.Spring.demo.model.Order;
import com.Spring.demo.service.CustomerService;
import com.Spring.demo.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private final OrderService orderService;

    public CustomerController(CustomerService service, OrderService orderService) {
        this.service = service;
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customersPage = service.getAllCustomers(pageable);

        if (customersPage.isEmpty()) {

            return ResponseEntity
                    .status(204) 
                    .body("No customers found on this page.");
        }

        return ResponseEntity.ok(customersPage);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Customer> getOne(@PathVariable Long id) {
        return service.getCustomerById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        try {
            Customer saved = service.createCustomer(customer);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updated = service.updateCustomer(id, customer);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersByCustomer(id));
    }
}
