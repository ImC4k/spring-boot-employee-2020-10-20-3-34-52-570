package com.thoughtworks.springbootemployee.mapper;

import com.thoughtworks.springbootemployee.dto.CompanyRequest;
import com.thoughtworks.springbootemployee.dto.CompanyResponse;
import com.thoughtworks.springbootemployee.dto.EmployeeResponseWithoutCompanyId;
import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.service.CompanyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompanyMapper {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private EmployeeMapper employeeMapper;

    public Company toEntity(CompanyRequest companyRequest) {
        Company company = new Company();
        BeanUtils.copyProperties(companyRequest, company);
        return company;
    }

    public CompanyResponse toResponse(Company company, List<EmployeeResponseWithoutCompanyId> employeeResponseWithoutCompanyId) {
        return new CompanyResponse(company.getId(), company.getCompanyName(), employeeResponseWithoutCompanyId);
    }
}
