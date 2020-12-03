package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public class Company {
    @MongoId
    private String id;
    private String name;
    private String address;
    private List<Employee> employees;

    public Company() {
    }

    public Company(String id, String name, String address, List<Employee> employees) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.employees = employees;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Company) {
            return this.id.equals(((Company) obj).getId()) &&
                    this.name.equals(((Company) obj).getName()) &&
                    this.employees.equals(((Company) obj).getEmployees()) &&
                    this.address.equals(((Company) obj).getAddress());
        }
        return super.equals(obj);
    }
}
