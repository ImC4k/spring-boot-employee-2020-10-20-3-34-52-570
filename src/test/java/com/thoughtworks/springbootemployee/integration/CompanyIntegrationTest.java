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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        employeeRepository.deleteAll();
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

    @Test
    void should_return_particular_company_when_get_one_given_valid_id_in_path() throws Exception {
        //given
        Company company1 = new Company("Tesla");
        Company expected1 = companyRepository.save(company1);
        Company company2 = new Company("SpaceX");
        companyRepository.save(company2);
        Employee employee = new Employee("Calvin", 19, "male", 999, expected1.getId());
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/companies/" + expected1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected1.getId()))
                .andExpect(jsonPath("$.companyName").value(expected1.getCompanyName()))
                .andExpect(jsonPath("$.employeesNumber").value(1))
                .andExpect(jsonPath("$.employees[0].name").value("Calvin"))
                .andExpect(jsonPath("$.employees[0].age").value(19))
                .andExpect(jsonPath("$.employees[0].gender").value("male"))
                .andExpect(jsonPath("$.employees[0].salary").value(999))
                .andExpect(jsonPath("$.employees[0].companyId").value(expected1.getId()));
    }

    @Test
    void should_return_not_found_when_get_one_given_invalid_id_in_path() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(get("/companies/100000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_paginated_companyList_when_getWithPagination_given_longer_than_pageSize_companyList() throws Exception {
        //given
        companyRepository.save(new Company("paypal"));
        companyRepository.save(new Company("tesla"));
        companyRepository.save(new Company("spacex"));
        companyRepository.save(new Company("boring"));
        companyRepository.save(new Company("openai"));

        //when
        //then
        mockMvc.perform(get("/companies?page=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("spacex"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].companyName").value("boring"));
    }

    @Test
    void should_return_paginated_companyList_when_getWithPagination_given_shorter_than_pageSize_companyList() throws Exception {
        //given
        companyRepository.save(new Company("paypal"));
        companyRepository.save(new Company("tesla"));

        //when
        //then
        mockMvc.perform(get("/companies?page=0&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].companyName").value("paypal"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].companyName").value("tesla"));
    }

    @Test
    void should_return_emptyList_companyList_when_getWithPagination_given_shorter_than_pageSize_companyList() throws Exception {
        //given
        companyRepository.save(new Company("paypal"));
        companyRepository.save(new Company("tesla"));

        //when
        //then
        mockMvc.perform(get("/companies?page=2&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }

    @Test
    void should_return_company_when_create_company_given_new_company() throws Exception {
        //given
        Company expected = new Company("tesla");
        String companyAsJson = "{\n" +
                "    \"companyName\": \"tesla\"\n" +
                "}";

        //when
        //then
        mockMvc
                .perform(
                        post("/companies")
                                .contentType(APPLICATION_JSON)
                                .content(companyAsJson)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.companyName").value("tesla"));

        List<Company> companyList = companyRepository.findAll();
        assertEquals(1, companyList.size());
        assertEquals(expected.getCompanyName(), companyList.get(0).getCompanyName());
    }

    @Test
    void should_return_updated_company_when_update_given_original_company_in_list() throws Exception {
        //given
        Company company = new Company("spacex");
        Company expected = companyRepository.save(company);

        String updateAsJson = "{\n" +
                "    \"companyName\": \"super spacex, mars ltd.\"\n" +
                "}";
        Company update = new Company("super spacex, mars ltd.");

        //when
        //then
        mockMvc
                .perform(
                        put("/companies/" + expected.getId())
                                .contentType(APPLICATION_JSON)
                                .content(updateAsJson)
                )
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.companyName").value(update.getCompanyName()));
    }

    @Test
    void should_return_not_found_when_update_given_invalid_id() throws Exception {
        //given
        String updateAsJson = "{\n" +
                "    \"companyName\": \"super spacex, mars ltd.\"\n" +
                "}";

        //when
        //then
        mockMvc
                .perform(
                        put("/companies/10000")
                                .contentType(APPLICATION_JSON)
                                .content(updateAsJson)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_ok_when_delete_given_a_company_is_deleted() throws Exception {
        //given
        Company company = new Company("tesla");
        Company expected = companyRepository.save(company);
        //when
        //then
        mockMvc
                .perform(
                        delete("/companies/" + expected.getId())
                )
                .andExpect(status().isNoContent());
    }

    @Test
    void should_return_empty_list_when_getAll_given_originally_one_company_but_then_got_deleted() throws Exception {
        //given
        Company company = new Company("tesla");
        Company expected = companyRepository.save(company);

        //when
        mockMvc
                .perform(
                        delete("/companies/" + expected.getId())
                );

        //then
        mockMvc
                .perform(
                        get("/companies")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void should_return_not_found_when_delete_given_invalid_id() throws Exception {
        //given
        //when
        //then
        mockMvc
                .perform(
                        delete("/companies/1000")
                )
                .andExpect(status().isNotFound());
    }
}
