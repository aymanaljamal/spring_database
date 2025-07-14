// repository/EmployeeRepository.java
package com.Spring.demo.repository;

import com.Spring.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
