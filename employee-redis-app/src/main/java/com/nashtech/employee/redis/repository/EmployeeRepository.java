package com.nashtech.employee.redis.repository;

import com.nashtech.employee.redis.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}