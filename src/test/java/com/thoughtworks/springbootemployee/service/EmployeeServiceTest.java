package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @InjectMocks
    private EmployeeService employeeService;
    @Mock
    private EmployeeRepository employeeRepository;

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
    void should_return_all_employees_when_getAll_given_all_employees() {
        //given
        final List<Employee> expected = Collections.singletonList(new Employee("123", "test", 16, "male", 1000));
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
        when(employeeRepository.findAll()).thenReturn(createDummyEmployees());

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
        when(employeeRepository.findAll()).thenReturn(createDummyEmployees());

        //when
        List<Employee> actualList = employeeService.getWithGenderFilter("male");

        //then
        assertTrue(actualList.stream().allMatch(actual -> actual.getGender().equals("male")));
    }

    @Test
    void should_return_created_employee_when_createEmployee_given_no_employee_in_database_and_a_new_employee() {
        //given
        Employee employee = new Employee("123", "test", 15, "male", 10000);
        when(employeeRepository.create(employee)).thenReturn(employee);

        //when
        employeeService.create(employee);
        final ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository, times(1)).create(employeeArgumentCaptor.capture());

        //then
        final Employee actual = employeeArgumentCaptor.getValue();
        assertEquals(employee, actual);
    }
    
    @Test
    void should_return_updated_employee_when_update_given_new_employee_and_even_though_update_employee_id_mismatch() {
        //given
        Employee updatedEmployee = new Employee("3", "new name", 15, "male", 999);
        Employee expected = new Employee("2", "new name", 15, "male", 999);
        when(employeeRepository.findAll()).thenReturn(createDummyEmployees());
        when(employeeRepository.create(any())).thenCallRealMethod();

        //when
        Employee actual = employeeService.update("2", updatedEmployee);
        
        //then
        assertEquals("2", actual.getId());
        assertEquals("new name", actual.getName());
        assertEquals(15, actual.getAge());
        assertEquals("male", actual.getGender());
        assertEquals(999, actual.getSalary());
    }
}
