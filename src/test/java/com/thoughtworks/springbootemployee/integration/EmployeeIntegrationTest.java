package com.thoughtworks.springbootemployee.integration;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeIntegrationTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private EmployeeRepository employeeRepository;

    @Test
    void should_return_all_employees_when_get_all_given_repository_has_employees() throws Exception {
        //given
        Employee employee = new Employee("123", "Calvin", 19, "male", 999, "1");
        employeeRepository.save(employee);

        //when
        //then
        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].name").value("Calvin"))
                .andExpect(jsonPath("$[0].age").value(19))
                .andExpect(jsonPath("$[0].gender").value("male"))
                .andExpect(jsonPath("$[0].salary").value(999))
                .andExpect(jsonPath("$[0].companyId").value("1"));
    }
}
