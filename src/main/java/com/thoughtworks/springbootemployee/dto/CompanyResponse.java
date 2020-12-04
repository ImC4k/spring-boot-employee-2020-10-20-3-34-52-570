package com.thoughtworks.springbootemployee.dto;

import java.util.List;

public class CompanyResponse {
    private String id;
    private String companyName;
    private Integer employeesNumber;
    private List<EmployeeResponseWithoutCompanyId> employees;

    public CompanyResponse(String id, String companyName, List<EmployeeResponseWithoutCompanyId> employees) {
        this.id = id;
        this.companyName = companyName;
        this.employeesNumber = employees.size();
        this.employees = employees;
    }

    public CompanyResponse() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Integer getEmployeesNumber() {
        return employeesNumber;
    }

    public List<EmployeeResponseWithoutCompanyId> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CompanyResponse) {
            return this.id.equals(((CompanyResponse) obj).getId()) &&
                    this.companyName.equals(((CompanyResponse) obj).getCompanyName()) &&
                    this.employeesNumber.equals(((CompanyResponse) obj).getEmployeesNumber()) &&
                    this.employees.equals(((CompanyResponse) obj).getEmployees());
        }
        return super.equals(obj);
    }
}
