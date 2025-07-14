package com.Spring.demo.controller;
import com.Spring.demo.model.Order;
import com.Spring.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long customerId,
            @RequestParam Long productId
    ) {
        Order order = orderService.createOrder(customerId, productId);
        return (order != null) ? ResponseEntity.ok(order) : ResponseEntity.badRequest().build();
    }


    @PostMapping("/{orderId}/approve")
    public ResponseEntity<Order> approveOrder(
            @PathVariable Long orderId,
            @RequestParam Long employeeId
    ) {
        Order approved = orderService.approveOrder(orderId, employeeId);
        return (approved != null) ? ResponseEntity.ok(approved) : ResponseEntity.notFound().build();
    }


    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }
}
