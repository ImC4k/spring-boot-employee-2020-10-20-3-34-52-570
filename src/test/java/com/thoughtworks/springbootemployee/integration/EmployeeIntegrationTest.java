package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

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

    @Test
    void should_return_all_employees_when_get_all_given_repository_has_employees() throws Exception {
        //given
        Employee employee = new Employee("123", "Calvin", 19, "male", 999, "1");
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
    void should_return_employee_when_create_employee_given_new_employee() throws Exception {
        //given
        String employeeAsJson = "{\n" +
                "    \"id\": \"789\",\n" +
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
    }
}
