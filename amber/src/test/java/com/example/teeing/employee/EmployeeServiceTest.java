package com.example.teeing.employee;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmployeeServiceTest {
    EmployeeService employeeService = new EmployeeService();

    @Test
    void getAvgEmployeeSalary() {

        List<Employee> employees = List.of(
                new Employee(10),
                new Employee(20),
                new Employee(30),
                new Employee(40),
                new Employee(50)
        );

        double result = employeeService.getAvgEmployeeSalary(employees);

        assertEquals(30.0, result);
    }
}
