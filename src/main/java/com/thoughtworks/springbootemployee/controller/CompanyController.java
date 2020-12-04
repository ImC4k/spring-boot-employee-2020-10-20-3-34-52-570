package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.EmployeeResponseWithoutCompanyId;
import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.mapper.CompanyMapper;
import com.thoughtworks.springbootemployee.mapper.EmployeeMapper;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired private CompanyService companyService;
    @Autowired private CompanyMapper companyMapper;
    @Autowired private EmployeeMapper employeeMapper;

    @GetMapping
    public List<CompanyResponse> getAll() {
        return companyService.getAll().stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CompanyResponse getOne(@PathVariable String id) throws CompanyNotFoundException {
        return companyMapper.toResponse(companyService.getOne(id));
    }

    @GetMapping(params = {"page", "pageSize"})
    public List<CompanyResponse> getWithPaging(@RequestParam("page") Integer pageNumber, @RequestParam("pageSize") Integer pageSize) {
        return companyService.getWithPagination(pageNumber, pageSize).stream().map(companyMapper::toResponse).collect(Collectors.toList());
    }

    @GetMapping("/{id}/employees")
    public List<EmployeeResponseWithoutCompanyId> getCompanyEmployees(@PathVariable("id") String id) {
        return companyService.getEmployeesFrom(id).stream().map(employeeMapper::toResponseWithoutCompanyId).collect(Collectors.toList());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyResponse createNewCompany(@RequestBody CompanyRequest newCompany) {
        return companyMapper.toResponse(companyService.create(companyMapper.toEntity(newCompany)));
    }

    @PutMapping("/{id}")
    public CompanyResponse updateCompany(@PathVariable String id, @RequestBody CompanyRequest companyUpdate) throws CompanyNotFoundException {
        return companyMapper.toResponse(companyService.update(id, companyMapper.toEntity(companyUpdate)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompany(@PathVariable String id) throws CompanyNotFoundException {
        companyService.remove(id);
    }
}
