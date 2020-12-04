package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.ResourceNotFoundException;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired private EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAll() {
        return employeeService.getAll();
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable String id) throws ResourceNotFoundException {
        return employeeService.getOne(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getWithPagination(pageNumber, pageSize);
    }

    @GetMapping(params = {"gender"})
    public List<Employee> getWithGenderFilter(@RequestParam("gender") String gender) {
        return employeeService.getWithGenderFilter(gender);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createNewEmployee(@RequestBody Employee newEmployee) {
        return employeeService.create(newEmployee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable String id, @RequestBody Employee employeeUpdate) throws ResourceNotFoundException {
        return employeeService.update(id, employeeUpdate);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable String id) throws ResourceNotFoundException{
        employeeService.remove(id);
    }
}
