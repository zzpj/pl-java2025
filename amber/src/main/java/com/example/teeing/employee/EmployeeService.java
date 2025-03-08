package com.example.teeing.employee;

import java.util.List;


class Employee {
    private double salary;

    public Employee(double salary) {
        this.salary = salary;
    }

    public double getSalary() {
        return salary;
    }
}

public class EmployeeService {

    double getAvgEmployeeSalary(List<Employee> employees) {
        // TODO: implement here
        // use List::stream method

        return 0.0;
    }
}
