package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.exception.CompanyNotFoundException;
import com.thoughtworks.springbootemployee.model.Company;
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

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getOne(String id) throws CompanyNotFoundException {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> getWithPagination(int pageNumber, int pageSize) {
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

    public void remove(String id) throws CompanyNotFoundException {
        companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        companyRepository.deleteById(id);
    }

    public Company update(String id, Company updatedCompany) throws CompanyNotFoundException {
        companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
        updatedCompany.setId(id);
        return companyRepository.save(updatedCompany);
    }
}
