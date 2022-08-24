package com.herren.gongassistance.employee.domain.repository;

import com.herren.gongassistance.employee.domain.entity.Employee;
import com.herren.gongassistance.shop.domain.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByTel(String tel);
}
