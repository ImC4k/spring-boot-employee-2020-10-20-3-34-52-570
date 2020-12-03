package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.CompanyCreate;
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
        CompanyResponse createdCompany = companyService.getOne(id);
        if (createdCompany == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(createdCompany, HttpStatus.OK);
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
    public CompanyCreate createNewCompany(@RequestBody CompanyCreate newCompanyCreate) {
        return companyService.create(newCompanyCreate);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyCreate> updateCompany(@PathVariable String id, @RequestBody CompanyCreate companyCreateUpdate) {
        CompanyCreate updatedCompanyCreate = companyService.update(id, companyCreateUpdate);
        if (updatedCompanyCreate == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCompanyCreate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable String id) {
        companyService.remove(id);
    }
}
