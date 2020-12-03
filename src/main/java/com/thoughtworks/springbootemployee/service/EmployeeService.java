package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    @Autowired private EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public Employee getOne(String id) {
        return employeeRepository.findById(id);
    }

    public List<Employee> getWithPagination(int pageNumber, int pageSize) {
        int pageToSkip = pageNumber - 1;
        return employeeRepository.findAll().stream()
                .skip((long) pageToSkip * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getWithGenderFilter(String gender) {
        return employeeRepository.findAll().stream().filter(employee -> employee.getGender().equals(gender)).collect(Collectors.toList());
    }

    public Employee update(String id, Employee updatedEmployee) {
        int numberOfDeletedEmployee = remove(id);
        if (numberOfDeletedEmployee == 0) {
            return null;
        }
        updatedEmployee.setId(id);
        return employeeRepository.save(updatedEmployee);
    }

    public Integer remove(String id) {
        return employeeRepository.remove(id);
    }
}
