package com.thoughtworks.springbootemployee.service;

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

    public Company getOne(String id) {
        return companyRepository.findById(id).orElse(null);
    }

    public List<Company> getWithPagination(int pageNumber, int pageSize) {
        int pageToSkip = pageNumber - 1;
        return companyRepository.findAll().stream()
                .skip((long) pageToSkip * pageSize)
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
        originalCompany.setName(updatedCompany.getName());
        originalCompany.setAddress(updatedCompany.getAddress());
        companyRepository.save(originalCompany);
        return originalCompany;
    }
}
