package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private final List<Employee> employees = new ArrayList<>();

    public List<Employee> findAll() {
        return this.employees;
    }

    public Employee findOne(String id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public Employee create(Employee newEmployee) {
        findAll().add(newEmployee);
        return newEmployee;
    }

    public Integer remove(String id) {
        Employee employeesWithId = findAll().stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
        if (employeesWithId == null) {
            return 0;
        }
        findAll().remove(employeesWithId);
        return 1;
    }
}
