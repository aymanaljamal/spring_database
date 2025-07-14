package com.Spring.demo.repository;

import com.Spring.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCustomerId(Long customerId);
    List<Order> findByApproved(boolean approved);
    List<Order> findByApprovedById(Long employeeId);
}
