package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired private CompanyService companyService;


    @GetMapping
    public List<Company> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public Company getOne(@PathVariable String id) {
        return companyService.getOne(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<Company> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return companyService.getWithPagination(pageNumber, pageSize);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@RequestParam("id") String id) {
        return companyService.getEmployeesFrom(id);
    }

    @PostMapping
    public Company createNewCompany(@RequestBody Company newCompany) {
        return companyService.create(newCompany);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company companyUpdate) {
        return companyService.update(id, companyUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.remove(id);
    }
}