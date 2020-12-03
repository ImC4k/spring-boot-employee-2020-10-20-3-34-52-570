package com.thoughtworks.springbootemployee.service;

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

    public CompanyResponse getOne(String id) {

        Company company = companyRepository.findById(id).orElse(null);
        if (company == null) {
            return null;
        }
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

    public void remove(String id) {
        companyRepository.deleteById(id);
    }

    public Company update(String id, Company updatedCompany) {
        Company originalCompany = companyRepository.findById(id).orElse(null);
        if (originalCompany == null) {
            return null;
        }
        updatedCompany.setId(id);
        companyRepository.save(updatedCompany);
        return updatedCompany;
    }
}
