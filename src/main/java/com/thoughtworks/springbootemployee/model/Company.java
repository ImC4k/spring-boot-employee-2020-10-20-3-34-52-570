package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Company {
    @MongoId
    private String id;
    private String companyName;
    private String address;

    public Company(String id, String companyName, String address) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
    }

    public Company() {}

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Company) {
            return this.id.equals(((Company) obj).getId()) &&
                    this.companyName.equals(((Company) obj).getCompanyName()) &&
                    this.address.equals(((Company) obj).getAddress());
        }
        return super.equals(obj);
    }
}
