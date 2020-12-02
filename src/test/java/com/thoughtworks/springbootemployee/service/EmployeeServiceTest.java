package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    void should_return_all_employees_when_getAll_given_all_employees() {
        //given
        final List<Employee> expected = Arrays.asList(new Employee("123", "test", 16, "male", 1000));
        when(employeeRepository.findAll()).thenReturn(expected);

        //when
        final List<Employee> employeeList = employeeService.getAll();

        //then
        assertEquals(1, employeeList.size());
    }

    @Test
    void should_return_the_employee_when_getOne_given_a_valid_employee_id() {
        //given
        Employee expected = new Employee("123", "test", 16, "male", 1000);
        when(employeeRepository.findOne(any())).thenReturn(expected);

        //when
        final Employee actual = employeeService.getOne("123");

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_null_when_getOne_given_a_invalid_employee_id() {
        //given
        Employee expected = new Employee("123", "test", 16, "male", 1000);
        when(employeeRepository.findOne("456")).thenReturn(null);

        //when
        final Employee actual = employeeService.getOne("456");

        //then
        assertNotEquals(expected, actual);
        assertNull(actual);
    }

    @Test
    void should_return_3_employees_when_getAllEmployeesWithPagination_given_employees_list_is_longer_than_3_and_pageNumber_is_1_and_pageSize_is_3() {
        //given
        List<Employee> allEmployees = createDummyEmployees();

        when(employeeRepository.findAll()).thenReturn(allEmployees);

        //when
        List<Employee> actual = employeeService.getWithPagination(1, 3);

        //then
        assertEquals(3, actual.size());
        assertEquals("1", actual.get(0).getId());
        assertEquals("2", actual.get(1).getId());
        assertEquals("3", actual.get(2).getId());
    }

    @Test
    void should_return_male_only_employees_when_getWithGenderFilter_given_non_empty_employees_with_both_gender_and_filter_is_male() {
        //given
        List<Employee> allEmployees = createDummyEmployees();
        when(employeeRepository.findAll()).thenReturn(allEmployees);

        //when
        List<Employee> actualList = employeeService.getWithGenderFilter("male");

        //then
        assertTrue(actualList.stream().allMatch(actual -> actual.getGender().equals("male")));
    }

    private List<Employee> createDummyEmployees() {
        List<Employee> allEmployees = new ArrayList<>();
        Employee employee1 = new Employee("1", "test", 16, "male", 1000);
        Employee employee2 = new Employee("2", "test", 16, "male", 1000);
        Employee employee3 = new Employee("3", "test", 16, "female", 1000);
        Employee employee4 = new Employee("4", "test", 16, "female", 1000);
        allEmployees.add(employee1);
        allEmployees.add(employee2);
        allEmployees.add(employee3);
        allEmployees.add(employee4);
        return allEmployees;
    }

    @Test
    void should_return_created_employee_when_createEmployee_given_no_employee_in_database_and_a_new_employee() {
        //given
        Employee expected = new Employee("123", "test", 15, "male", 10000);
        when(employeeRepository.create(expected)).thenReturn(expected);

        //when
        final Employee actual = employeeService.create(expected);

        //then
        assertEquals(expected, actual);
    }
}
