package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CompanyIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        companyRepository.deleteAll();
    }

    @Test
    void should_return_all_employees_when_get_all_given_repository_has_employees() throws Exception {
        //given
        Company company1 = new Company("Tesla");
        Company expected1 = companyRepository.save(company1);
        Company company2 = new Company("SpaceX");
        Company expected2 = companyRepository.save(company2);
        Employee employee = new Employee("Calvin", 19, "male", 999, expected1.getId());
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/companies"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(expected1.getId()))
                .andExpect(jsonPath("$[0].companyName").value(expected1.getCompanyName()))
                .andExpect(jsonPath("$[0].employeesNumber").value(1))
                .andExpect(jsonPath("$[0].employees[0].name").value("Calvin"))
                .andExpect(jsonPath("$[0].employees[0].age").value(19))
                .andExpect(jsonPath("$[0].employees[0].gender").value("male"))
                .andExpect(jsonPath("$[0].employees[0].salary").value(999))
                .andExpect(jsonPath("$[0].employees[0].companyId").value(expected1.getId()))
                .andExpect(jsonPath("$[1].id").value(expected2.getId()))
                .andExpect(jsonPath("$[1].companyName").value("SpaceX"))
                .andExpect(jsonPath("$[1].employeesNumber").value(0));
    }
}
