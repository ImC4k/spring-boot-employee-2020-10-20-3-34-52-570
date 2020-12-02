package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class CompanyService {
    @Autowired private CompanyRepository companyRepository;

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getOne(String id) {
        return companyRepository.findOne(id);
    }

    public List<Company> getWithPagination(int pageNumber, int pageSize) {
        int pageToSkip = pageNumber - 1;
        return companyRepository.findAll().stream()
                .skip((long) pageToSkip * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesFrom(String companyId) {
        Company targetCompany = companyRepository.findOne(companyId);
        if (targetCompany == null) {
            return null;
        }
        return targetCompany.getEmployees();
    }

    public Company create(Company newCompany) {
        return companyRepository.create(newCompany);
    }

    public Integer remove(String id) {
        return companyRepository.remove(id);
    }

    public Company update(String id, Company updatedCompany) {
        int numberOfDeletedEmployee = remove(id);
        if (numberOfDeletedEmployee == 0) {
            return null;
        }
        updatedCompany.setId(id);
        return companyRepository.create(updatedCompany);
    }
}
