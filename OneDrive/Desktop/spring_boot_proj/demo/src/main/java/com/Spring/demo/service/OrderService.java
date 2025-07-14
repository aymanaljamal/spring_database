package com.Spring.demo.service;
import com.Spring.demo.model.Customer;
import com.Spring.demo.model.Employee;
import com.Spring.demo.model.Order;
import com.Spring.demo.model.Product;
import com.Spring.demo.repository.CustomerRepository;
import com.Spring.demo.repository.EmployeeRepository;
import com.Spring.demo.repository.OrderRepository;
import com.Spring.demo.repository.ProductRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Service
public class OrderService {
    private final OrderRepository orderRepo;
    private final CustomerRepository customerRepo;
    private final ProductRepository productRepo;
    private final EmployeeRepository employeeRepo;
    public OrderService(OrderRepository orderRepo,
                        CustomerRepository customerRepo,
                        ProductRepository productRepo,
                        EmployeeRepository employeeRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
        this.productRepo = productRepo;
        this.employeeRepo = employeeRepo;
    }
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepo.findByCustomerId(customerId);
    }
    public Order createOrder(Long customerId, Long productId) {
        Optional<Customer> customer = customerRepo.findById(customerId);
        Optional<Product> product = productRepo.findById(productId);
        if (customer.isEmpty() || product.isEmpty()) return null;
        Order order = new Order();
        order.setCustomer(customer.get());
        order.setProduct(product.get());
        order.setApproved(false);
        return orderRepo.save(order);
    }
    public Order approveOrder(Long orderId, Long employeeId) {
        Optional<Order> orderOpt = orderRepo.findById(orderId);
        Optional<Employee> employeeOpt = employeeRepo.findById(employeeId);
        if (orderOpt.isEmpty() || employeeOpt.isEmpty()) return null;
        Order order = orderOpt.get();
        order.setApproved(true);
        order.setApprovedBy(employeeOpt.get());
        return orderRepo.save(order);
    }
    public void deleteOrder(Long id) {
        if (orderRepo.existsById(id)) {
            orderRepo.deleteById(id);
        }
    }
    public List<Order> getOrdersApprovedByEmployee(Long employeeId) {
        return orderRepo.findByApprovedById(employeeId);
    }
}
