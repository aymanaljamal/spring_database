package com.Spring.demo.service;
import com.Spring.demo.model.Employee;
import com.Spring.demo.model.Order;
import com.Spring.demo.model.Product;
import com.Spring.demo.repository.EmployeeRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.OneToMany;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Service
public class EmployeeService {
    private final EmployeeRepository repo;
    public EmployeeService(EmployeeRepository repo) {
        this.repo = repo;
    }
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }
    public Optional<Employee> getEmployeeById(Long id) {
        return repo.findById(id);
    }
    public Employee createEmployee(Employee employee) {
        return repo.save(employee);
    }
    public Employee updateEmployee(Long id, Employee updated) {
        return repo.findById(id).map(emp -> {
            emp.setName(updated.getName());
            emp.setEmail(updated.getEmail());
            emp.setRole(updated.getRole());
            return repo.save(emp);
        }).orElse(null);
    }
    public void deleteEmployee(Long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        }
    }
    public List<Product> getCreatedProducts(Long employeeId) {
        return getEmployeeById(employeeId)
                .map(Employee::getProducts)
                .orElse(Collections.emptyList());
    }

    public List<Order> getApprovedOrders(Long employeeId) {
        return getEmployeeById(employeeId)
                .map(Employee::getApprovedOrders)
                .orElse(Collections.emptyList());
    }

}
