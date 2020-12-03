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

    public Employee findById(String id) {
        return findAll().stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    public Employee save(Employee newEmployee) {
        findAll().add(newEmployee);
        return newEmployee;
    }

    public Integer remove(String id) {
        Employee employeeWithId = findAll().stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
        if (employeeWithId == null) {
            return 0;
        }
        findAll().remove(employeeWithId);
        return 1;
    }
}
