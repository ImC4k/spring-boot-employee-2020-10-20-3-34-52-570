package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.CompanyCreate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<CompanyCreate, String> {
}
