package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void should_return_all_employees_when_get_all_given_repository_has_employees() throws Exception {
        //given
        Employee employee = new Employee("Calvin", 19, "male", 999, "1");
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Calvin"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(999))
                .andExpect(jsonPath("$[0].companyId").value("1"));
    }

    @Test
    void should_particular_employee_when_get_one_given_valid_id_in_path() throws Exception {
        //given
        employeeRepository.save(new Employee("Calvin", 19, "male", 999, "1"));
        employeeRepository.save(new Employee("Boyd", 19, "male", 999, "2"));
        Employee employee = new Employee("Calvin", 19, "male", 999, "3");
        Employee expected = employeeRepository.save(employee);
        employeeRepository.save(new Employee("David", 19, "male", 999, "4"));
        employeeRepository.save(new Employee("Elaine", 19, "female", 999, "5"));
        employeeRepository.save(new Employee("Flora", 19, "female", 999, "6"));

        //when
        //then
        mockMvc.perform(get("/employees/" + expected.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name").value("Calvin"))
                .andExpect(jsonPath("$.age").value(19))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(999))
                .andExpect(jsonPath("$.companyId").value("3"));
    }

    @Test
    void should_particular_employee_when_get_one_given_invalid_id_in_path() throws Exception {
        //given
        employeeRepository.save(new Employee("Calvin", 19, "male", 999, "1"));
        employeeRepository.save(new Employee("Boyd", 19, "male", 999, "2"));
        Employee employee = new Employee("Calvin", 19, "male", 999, "3");
        Employee expected = employeeRepository.save(employee);
        employeeRepository.save(new Employee("David", 19, "male", 999, "4"));
        employeeRepository.save(new Employee("Elaine", 19, "female", 999, "5"));
        employeeRepository.save(new Employee("Flora", 19, "female", 999, "6"));

        //when
        //then
        mockMvc.perform(get("/employees/100000"))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_employee_when_create_employee_given_new_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"name\": \"John\",\n" +
                "    \"age\": 23,\n" +
                "    \"gender\": \"male\",\n" +
                "    \"salary\": 19999,\n" +
                "    \"companyId\": \"1\"\n" +
                "}";

        //when
        //then
        mockMvc
                .perform(
                        post("/employees")
                                .contentType(APPLICATION_JSON)
                                .content(employeeAsJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(23))
                .andExpect(jsonPath("$.gender").value("male"))
                .andExpect(jsonPath("$.salary").value(19999))
                .andExpect(jsonPath("$.companyId").value("1"));

        List<Employee> employeeList = employeeRepository.findAll();
        assertEquals(1, employeeList.size());
        assertEquals("John", employeeList.get(0).getName());
        // ... assert other fields
    }

    @Test
    void should_return_paginated_employeeList_when_getWithPagination_given_longer_than_pageSize_employeeList() throws Exception {
        //given
        employeeRepository.save(new Employee("Alex", 19, "male", 999, "1"));
        employeeRepository.save(new Employee("Boyd", 19, "male", 999, "2"));
        employeeRepository.save(new Employee("Calvin", 19, "male", 999, "3"));
        employeeRepository.save(new Employee("David", 19, "male", 999, "4"));
        employeeRepository.save(new Employee("Elaine", 19, "female", 999, "5"));
        employeeRepository.save(new Employee("Flora", 19, "female", 999, "6"));

        //when
        //then
        mockMvc.perform(get("/employees?page=1&pageSize=2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Calvin"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(999))
                .andExpect(jsonPath("$[0].companyId").value("3"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("David"))
                .andExpect(jsonPath("$[1].age").value(19))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].salary").value(999))
                .andExpect(jsonPath("$[1].companyId").value("4"));
    }

    @Test
    void should_return_paginated_employeeList_when_getWithPagination_given_shorter_than_pageSize_employeeList() throws Exception {
        //given
        employeeRepository.save(new Employee("Alex", 19, "male", 999, "1"));
        employeeRepository.save(new Employee("Boyd", 19, "male", 999, "2"));

        //when
        //then
        mockMvc.perform(get("/employees?page=0&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(999))
                .andExpect(jsonPath("$[0].companyId").value("1"))
                .andExpect(jsonPath("$[1].id").isString())
                .andExpect(jsonPath("$[1].name").value("Boyd"))
                .andExpect(jsonPath("$[1].age").value(19))
                .andExpect(jsonPath("$[1].gender").value("male"))
                .andExpect(jsonPath("$[1].salary").value(999))
                .andExpect(jsonPath("$[1].companyId").value("2"));
    }

    @Test
    void should_return_emptyList_when_getWithPagination_given_pageNumber_exceeds_employeeList_size() throws Exception {
        //given
        employeeRepository.save(new Employee("Alex", 19, "male", 999, "1"));
        employeeRepository.save(new Employee("Boyd", 19, "male", 999, "2"));

        //when
        //then
        mockMvc.perform(get("/employees?page=2&pageSize=3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().string("[]"));
    }
}
