package com.thoughtworks.springbootemployee.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public class Employee {
    @MongoId(FieldType.OBJECT_ID)
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private String companyId;

    public Employee() {
    }

    public Employee(String id, String name, Integer age, String gender, Integer salary, String companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Employee) {
            return this.id.equals(((Employee) obj).getId()) &&
                    this.name.equals(((Employee) obj).getName()) &&
                    this.age.equals(((Employee) obj).getAge()) &&
                    this.gender.equals(((Employee) obj).getGender()) &&
                    this.salary.equals(((Employee) obj).getSalary()) &&
                    this.companyId.equals(((Employee) obj).getCompanyId());
        }
        return super.equals(obj);
    }
}
