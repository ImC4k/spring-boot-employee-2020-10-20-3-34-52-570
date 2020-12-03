package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.CompanyCreate;
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
        List<CompanyCreate> companyCreates = companyRepository.findAll();
        return companyCreates.stream().map(companyCreate -> {
            List<Employee> employees = getEmployeesFrom(companyCreate.getId());
            return new CompanyResponse(companyCreate.getId(), companyCreate.getCompanyName(), employees);
        }).collect(Collectors.toList());
    }

    public CompanyResponse getOne(String id) {

        CompanyCreate companyCreate = companyRepository.findById(id).orElse(null);
        if (companyCreate == null) {
            return null;
        }
        List<Employee> employeesFromThisCompany = getEmployeesFrom(companyCreate.getId());
        return new CompanyResponse(companyCreate.getId(), companyCreate.getCompanyName(), employeesFromThisCompany);
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

    public CompanyCreate create(CompanyCreate newCompanyCreate) {
        return companyRepository.save(newCompanyCreate);
    }

    public void remove(String id) {
        companyRepository.deleteById(id);
    }

    public CompanyCreate update(String id, CompanyCreate updatedCompanyCreate) {
        CompanyCreate originalCompanyCreate = companyRepository.findById(id).orElse(null);
        if (originalCompanyCreate == null) {
            return null;
        }
        updatedCompanyCreate.setId(id);
        companyRepository.save(updatedCompanyCreate);
        return updatedCompanyCreate;
    }
}
