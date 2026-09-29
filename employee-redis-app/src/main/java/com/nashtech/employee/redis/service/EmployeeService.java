package com.nashtech.employee.redis.service;

import com.nashtech.employee.redis.dto.EmployeeRequest;
import com.nashtech.employee.redis.dto.EmployeeResponse;
import com.nashtech.employee.redis.entity.EmployeeEntity;
import com.nashtech.employee.redis.repository.EmployeeRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    // CREATE
    public EmployeeResponse createEmployee(EmployeeRequest request) {

        EmployeeEntity employee = new EmployeeEntity();

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        EmployeeEntity savedEmployee = employeeRepository.save(employee);

        return toResponse(savedEmployee);
    }

    // GET ALL
    public List<EmployeeResponse> getAllEmployees() {

        return employeeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    // GET BY ID - Redis Cache
    @Cacheable(value = "employees", key = "#id")
    public EmployeeResponse getEmployeeById(Long id) {

        System.out.println("Fetching employee from database: " + id);

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));

        return toResponse(employee);
    }

    // UPDATE - Update Redis Cache
    @CachePut(value = "employees", key = "#id")
    public EmployeeResponse updateEmployee(Long id, EmployeeRequest request) {

        EmployeeEntity employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Employee not found with id: " + id));

        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setDepartment(request.getDepartment());
        employee.setSalary(request.getSalary());

        EmployeeEntity updatedEmployee = employeeRepository.save(employee);

        return toResponse(updatedEmployee);
    }

    // DELETE - Remove from Redis Cache
    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            throw new RuntimeException(
                    "Employee not found with id: " + id);
        }

        employeeRepository.deleteById(id);
    }

    private EmployeeResponse toResponse(EmployeeEntity employee) {

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        );
    }
}