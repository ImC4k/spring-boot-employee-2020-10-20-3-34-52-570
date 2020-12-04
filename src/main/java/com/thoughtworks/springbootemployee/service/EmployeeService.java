package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.ResourceNotFoundException;
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

    public Employee getOne(String id) throws ResourceNotFoundException {
        return employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public List<Employee> getWithPagination(int pageNumber, int pageSize) {
        return employeeRepository.findAll().stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee create(Employee newEmployee) {
        return employeeRepository.save(newEmployee);
    }

    public List<Employee> getWithGenderFilter(String gender) {
        return employeeRepository.findAllByGender(gender);
    }

    public Employee update(String id, Employee updatedEmployee) throws ResourceNotFoundException {
        employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedEmployee.setId(id);
        employeeRepository.save(updatedEmployee);
        return updatedEmployee;
    }

    public void remove(String id) throws ResourceNotFoundException {
        employeeRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        employeeRepository.deleteById(id);
    }
}
