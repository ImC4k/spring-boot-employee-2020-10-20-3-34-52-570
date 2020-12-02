package com.thoughtworks.springbootemployee.controller;

public class Employee {
    private String id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    public Employee() {
    }

    public Employee(String id, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public Integer getSalary() {
        return salary;
    }
}
