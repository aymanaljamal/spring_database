package com.Spring.demo.controller;

import com.Spring.demo.model.Employee;
import com.Spring.demo.model.Order;
import com.Spring.demo.model.Product;
import com.Spring.demo.service.EmployeeService;
import com.Spring.demo.service.OrderService;
import com.Spring.demo.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final ProductService productService;
    private final OrderService orderService;

    public EmployeeController(EmployeeService service, ProductService productService, OrderService orderService) {
        this.service = service;
        this.productService = productService;
        this.orderService = orderService;
    }
    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        System.out.println("\n===== All Employees =====");
        System.out.printf("%-5s | %-20s | %-30s | %-10s\n", "ID", "Name", "Email", "Role");
        System.out.println("--------------------------------------------------------------------------");
        for (Employee e : employees) {
            System.out.printf("%-5d | %-20s | %-30s | %-10s\n",
                    e.getId(), e.getName(), e.getEmail(), e.getRole());
        }
        System.out.println("==========================================================================\n");

        return ResponseEntity.ok(employees);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return service.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
        try {
            Employee saved = service.createEmployee(employee);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        Employee updated = service.updateEmployee(id, employee);
        return (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getEmployeeProducts(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductsByEmployeeId(id));
    }
    @GetMapping("/{id}/approved-orders")
    public ResponseEntity<List<Order>> getApprovedOrders(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrdersApprovedByEmployee(id));
    }
}
