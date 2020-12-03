package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.ResourceNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.CompanyResponse;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired private CompanyRepository companyRepository;
    @Autowired private EmployeeRepository employeeRepository;

    public List<CompanyResponse> getAll() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream().map(companyCreate -> {
            List<Employee> employees = getEmployeesFrom(companyCreate.getId());
            return new CompanyResponse(companyCreate.getId(), companyCreate.getCompanyName(), employees);
        }).collect(Collectors.toList());
    }

    public CompanyResponse getOne(String id) throws ResourceNotFoundException {

        Company company = companyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        List<Employee> employeesFromThisCompany = getEmployeesFrom(company.getId());
        return new CompanyResponse(company.getId(), company.getCompanyName(), employeesFromThisCompany);
    }

    public List<CompanyResponse> getWithPagination(int pageNumber, int pageSize) {
        return getAll().stream()
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesFrom(String companyId) {
        return employeeRepository.findAllByCompanyId(companyId);
    }

    public Company create(Company newCompany) {
        return companyRepository.save(newCompany);
    }

    public void remove(String id) throws ResourceNotFoundException {
        try {
            if (companyRepository.findById(id).isPresent()) {
                companyRepository.deleteById(id);
                return;
            }
        }
        catch(Exception ignore) {}
        throw new ResourceNotFoundException();
    }

    public Company update(String id, Company updatedCompany) throws ResourceNotFoundException {
        companyRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        updatedCompany.setId(id);
        companyRepository.save(updatedCompany);
        return updatedCompany;
    }
}
