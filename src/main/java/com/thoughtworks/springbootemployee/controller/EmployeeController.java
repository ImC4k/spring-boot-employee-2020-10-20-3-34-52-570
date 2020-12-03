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
    public ResponseEntity<Employee> getOne(@PathVariable String id) {
        try {
            Employee employee =  employeeService.getOne(id);
            if (employee != null) {
                return new ResponseEntity<>(employee, HttpStatus.OK);
            }
        }
        catch (Exception ignored) {}
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employeeUpdate) {
        try {
            Employee updatedEmployee = employeeService.update(id, employeeUpdate);
            if (updatedEmployee != null) {
                return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
            }
        }
        catch (Exception ignored) {}
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable String id) throws ReflectiveOperationException{
        employeeService.remove(id);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException() {

    }
}
