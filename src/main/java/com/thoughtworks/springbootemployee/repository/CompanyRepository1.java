package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository1 extends MongoRepository<Company, String> {
}
