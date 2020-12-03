package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class CompanyCreate {
    @MongoId
    private String id;
    private String companyName;

    public CompanyCreate(String id, String companyName) {
        this.id = id;
        this.companyName = companyName;
    }

    public CompanyCreate() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompanyCreate) {
            return this.id.equals(((CompanyCreate) obj).getId()) &&
                    this.companyName.equals(((CompanyCreate) obj).getCompanyName());
        }
        return super.equals(obj);
    }
}
