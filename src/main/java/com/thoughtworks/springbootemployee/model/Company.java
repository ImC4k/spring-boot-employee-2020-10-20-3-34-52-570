package com.thoughtworks.springbootemployee.model;

import java.util.List;

public class Company {
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

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public List<Employee> getEmployees() { return employees; }

    public void setId(String id) {
        this.id = id;
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
