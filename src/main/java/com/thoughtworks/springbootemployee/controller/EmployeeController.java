package com.thoughtworks.springbootemployee.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final List<Employee> employees = new ArrayList<>();

    @GetMapping
    public List<Employee> getAll() {
        return employees;
    }

    @GetMapping("/{id}")
    public Employee getOne(@PathVariable String id) {
        return employees.stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Employee> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        int pageToSkip = pageNumber - 1;
        return employees.stream()
                .skip((long) pageToSkip * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }
}
