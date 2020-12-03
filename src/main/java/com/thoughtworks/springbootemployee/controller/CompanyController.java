package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.exception.ResourceNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CompanyResponse> getOne(@PathVariable String id) {
        try {
            CompanyResponse createdCompany = companyService.getOne(id);
            if (createdCompany != null) {
                return new ResponseEntity<>(createdCompany, HttpStatus.OK);
            }
        }
        catch (Exception ignored){}
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Company> updateCompany(@PathVariable String id, @RequestBody Company companyUpdate) {
        try {
            Company updatedCompany = companyService.update(id, companyUpdate);
            if (updatedCompany != null) {
                return new ResponseEntity<>(updatedCompany, HttpStatus.OK);
            }
        }
        catch(Exception ignored) {}
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable String id) throws ResourceNotFoundException {
        companyService.remove(id);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleResourceNotFoundException() {

    }
}
