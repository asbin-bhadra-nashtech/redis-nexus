package com.nashtech.employee.redis.dto;

public class EmployeeResponse {

    private Long id;
    private String name;
    private String email;
    private String department;
    private Double salary;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long id,
            String name,
            String email,
            String department,
            Double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public Double getSalary() {
        return salary;
    }
}