package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.EmployeeRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponse;
import com.thoughtworks.springbootemployee.exception.EmployeeNotFoundException;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired private EmployeeService employeeService;
    @Autowired private EmployeeMapper employeeMapper;

    @GetMapping
    public List<EmployeeResponse> getAll() {
        return employeeService.getAll().stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public EmployeeResponse getOne(@PathVariable String id) throws EmployeeNotFoundException {
        return employeeMapper.toResponse(employeeService.getOne(id));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<EmployeeResponse> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return employeeService.getWithPagination(pageNumber, pageSize).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping(params = {"gender"})
    public List<EmployeeResponse> getWithGenderFilter(@RequestParam("gender") String gender) {
        return employeeService.getWithGenderFilter(gender).stream().map(employeeMapper::toResponse).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponse createNewEmployee(@RequestBody EmployeeRequest employeeRequest) {
        Employee employee = employeeService.create(employeeMapper.toEntity(employeeRequest));
        return employeeMapper.toResponse(employee);
    }

    @PutMapping("/{id}")
    public EmployeeResponse updateEmployee(@PathVariable String id, @RequestBody EmployeeRequest employeeUpdate) throws EmployeeNotFoundException {
        return employeeMapper.toResponse(employeeService.update(id, employeeMapper.toEntity(employeeUpdate)));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable String id) throws EmployeeNotFoundException {
        employeeService.remove(id);
    }
}
