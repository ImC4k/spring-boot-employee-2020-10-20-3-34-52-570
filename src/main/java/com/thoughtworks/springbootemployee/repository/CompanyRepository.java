package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {
    private final List<Company> companies = new ArrayList<>();

    public List<Company> findAll() {
        return this.companies;
    }

    public Company findOne(String id) {
        return companies.stream().filter(company -> company.getId().equals(id)).findFirst().orElse(null);
    }

    public Company create(Company newCompany) {
        findAll().add(newCompany);
        return newCompany;
    }

    public Integer remove(String id) {
        Company companyWithId = findAll().stream().filter(employee -> employee.getId().equals(id)).findFirst().orElse(null);
        if (companyWithId == null) {
            return 0;
        }
        findAll().remove(companyWithId);
        return 1;
    }
}
