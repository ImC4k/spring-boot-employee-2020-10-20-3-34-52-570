package com.thoughtworks.springbootemployee.service;

import com.thoughtworks.springbootemployee.model.Company;
import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.CompanyRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {
    @InjectMocks
    private CompanyService companyService;
    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private EmployeeRepository employeeRepository;

    private List<Company> createDummyCompanyies() {
        List<Company> allCompanies = new ArrayList<>();
        Company company1 = new Company("1", "company a", "some addr 1");
        Company company2 = new Company("2", "company b", "some addr 2");
        Company company3 = new Company("3", "company c", "some addr 3");
        Company company4 = new Company("4", "company d", "some addr 4");
        allCompanies.add(company1);
        allCompanies.add(company2);
        allCompanies.add(company3);
        allCompanies.add(company4);
        return allCompanies;
    }

    @Test
    void should_return_all_companies_when_getAll_given_all_companies() {
        //given
        final List<Company> expected = Collections.singletonList(new Company("123", "test", "addr"));
        when(companyRepository.findAll()).thenReturn(expected);

        //when
        final List<Company> companyList = companyService.getAll();

        //then
        assertEquals(1, companyList.size());
    }

    @Test
    void should_return_the_company_when_getOne_given_a_valid_company_id() {
        //given
        Company expected = new Company("123", "test", "addr");
        when(companyRepository.findById(any())).thenReturn(Optional.of(expected));

        //when
        final Company actual = companyService.getOne("123");

        //then
        assertEquals(expected, actual);
    }

    @Test
    void should_return_null_when_getOne_given_a_invalid_company_id() {
        //given
        Company expected = new Company("123", "test", "addr");
        when(companyRepository.findById("456")).thenReturn(Optional.empty());

        //when
        final Company actual = companyService.getOne("456");

        //then
        assertNotEquals(expected, actual);
        assertNull(actual);
    }

    @Test
    void should_return_3_companies_when_getAllCompaniesWithPagination_given_companies_list_is_longer_than_3_and_pageNumber_is_1_and_pageSize_is_3() {
        //given
        when(companyRepository.findAll()).thenReturn(createDummyCompanyies());

        //when
        List<Company> actual = companyService.getWithPagination(1, 3);

        //then
        assertEquals(3, actual.size());
        assertEquals("1", actual.get(0).getId());
        assertEquals("2", actual.get(1).getId());
        assertEquals("3", actual.get(2).getId());
    }

    @Test
    void should_return_employees_when_getEmployeesFrom_given_company_id() {
        //given
        List<Employee> employeesFromCompanyX = new ArrayList<>();
        Employee employee1 = new Employee("1", "test", 16, "male", 1000, "5");
        Employee employee2 = new Employee("2", "test", 16, "male", 1000, "5");
        Employee employee3 = new Employee("3", "test", 16, "female", 1000, "5");
        Employee employee4 = new Employee("4", "test", 16, "female", 1000, "5");
        employeesFromCompanyX.add(employee1);
        employeesFromCompanyX.add(employee2);
        employeesFromCompanyX.add(employee3);
        employeesFromCompanyX.add(employee4);
        when(employeeRepository.findAllByCompanyId(anyString())).thenReturn(employeesFromCompanyX);

        //when
        List<Employee> actualList = companyService.getEmployeesFrom("5");

        //then
        assertEquals(employeesFromCompanyX, actualList);
    }

    @Test
    void should_return_created_company_when_create_given_no_company_in_database_and_a_new_company() {
        //given
        Company company = new Company("123", "test", "addr");
        when(companyRepository.save(company)).thenReturn(company);

        //when
        companyService.create(company);
        final ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());

        //then
        final Company actual = companyArgumentCaptor.getValue();
        assertEquals(company, actual);
    }

    @Test
    void should_return_updated_company_when_update_given_new_company_and_even_though_update_company_id_mismatch() {
        //given
        Company updatedCompany = new Company("3", "new name", "addr");
        Company expected = new Company("2", "new name", "addr");
        when(companyRepository.findById("2")).thenReturn(Optional.of(expected));

        //when
        companyService.update("2", updatedCompany);
        final ArgumentCaptor<Company> companyArgumentCaptor = ArgumentCaptor.forClass(Company.class);
        verify(companyRepository, times(1)).save(companyArgumentCaptor.capture());

        //then
        final Company actual = companyArgumentCaptor.getValue();
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    void should_return_null_when_update_given_new_company_and_id_does_not_exist() {
        //given
        Company updatedCompany = new Company("5", "new name", "addr");
        when(companyRepository.findById(any())).thenReturn(Optional.empty());

        //when
        Company actual = companyService.update("5", updatedCompany);

        //then
        assertNull(actual);
    }

    @Test
    void should_return_1_and_have_a_company_removed_when_remove() {
        //given
        //when
        companyService.remove("1");

        //then
        verify(companyRepository, times(1)).deleteById("1");
    }
}