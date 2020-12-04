package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired private CompanyService companyService;

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public CompanyResponse getOne(@PathVariable String id) throws CompanyNotFoundException {
        return companyService.getOne(id);
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return companyService.getWithPagination(pageNumber, pageSize);
    }

    @GetMapping("/{id}/employees")
    public List<Employee> getCompanyEmployees(@PathVariable("id") String id) {
        return companyService.getEmployeesFrom(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Company createNewCompany(@RequestBody Company newCompany) {
        return companyService.create(newCompany);
    }

    @PutMapping("/{id}")
    public Company updateCompany(@PathVariable String id, @RequestBody Company companyUpdate) throws CompanyNotFoundException {
        return companyService.update(id, companyUpdate);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable String id) throws CompanyNotFoundException {
        companyService.remove(id);
    }
}
