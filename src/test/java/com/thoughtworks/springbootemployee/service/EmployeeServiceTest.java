package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
