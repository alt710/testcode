package com.example.companysearch.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CompanyTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        Company company = new Company();

        // Then
        assertNotNull(company);
        assertNull(company.getId());
        assertNull(company.getCompanyNumber());
        assertNull(company.getCompanyStatus());
        assertNull(company.getDateOfCreation());
        assertNull(company.getTitle());
        assertNull(company.getCompanyType());
        assertNull(company.getAddress());
        assertNull(company.getOfficers());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        List<Officer> officers = new ArrayList<>();
        Company company = new Company(
                1L,
                "12345678",
                "active",
                "2000-01-01",
                "Test Company",
                "private limited",
                address,
                officers
        );

        // Then
        assertNotNull(company);
        assertEquals(1L, company.getId());
        assertEquals("12345678", company.getCompanyNumber());
        assertEquals("active", company.getCompanyStatus());
        assertEquals("2000-01-01", company.getDateOfCreation());
        assertEquals("Test Company", company.getTitle());
        assertEquals("private limited", company.getCompanyType());
        assertEquals(address, company.getAddress());
        assertEquals(officers, company.getOfficers());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Company company = new Company();
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        List<Officer> officers = new ArrayList<>();

        // When
        company.setId(1L);
        company.setCompanyNumber("12345678");
        company.setCompanyStatus("active");
        company.setDateOfCreation("2000-01-01");
        company.setTitle("Test Company");
        company.setCompanyType("private limited");
        company.setAddress(address);
        company.setOfficers(officers);

        // Then
        assertEquals(1L, company.getId());
        assertEquals("12345678", company.getCompanyNumber());
        assertEquals("active", company.getCompanyStatus());
        assertEquals("2000-01-01", company.getDateOfCreation());
        assertEquals("Test Company", company.getTitle());
        assertEquals("private limited", company.getCompanyType());
        assertEquals(address, company.getAddress());
        assertEquals(officers, company.getOfficers());
    }

    @Test
    void testAddOfficerToCompany() {
        // Given
        Company company = new Company();
        Officer officer = new Officer(
                "John Doe",
                "Director",
                "2022-01-01",
                "2023-01-01",
                "Engineer",
                "UK",
                "British",
                new Address("Locality", "12345", "Premises", "Address Line 1", "Country"),
                new DateOfBirth(1990, 5)
        );

        List<Officer> officers = new ArrayList<>();
        officers.add(officer);

        // When
        company.setOfficers(officers);
        officer.setCompany(company);

        // Then
        assertNotNull(company.getOfficers());
        assertEquals(1, company.getOfficers().size());
        assertEquals(officer, company.getOfficers().get(0));
        assertEquals(company, officer.getCompany());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        List<Officer> officers = new ArrayList<>();
        Company company1 = new Company(
                1L,
                "12345678",
                "active",
                "2000-01-01",
                "Test Company",
                "private limited",
                address,
                officers
        );

        Company company2 = new Company(
                1L,
                "12345678",
                "active",
                "2000-01-01",
                "Test Company",
                "private limited",
                address,
                officers
        );

        // Then
        assertEquals(company1, company2);
        assertEquals(company1.hashCode(), company2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        Company company = new Company(
                1L,
                "12345678",
                "active",
                "2000-01-01",
                "Test Company",
                "private limited",
                address,
                new ArrayList<>()
        );

        // Then
        String expected = "Company(id=1, companyNumber=12345678, companyStatus=active, dateOfCreation=2000-01-01, title=Test Company, companyType=private limited, address=Address(locality=Locality, postal_code=12345, premises=Premises, address_line_1=Address Line 1, country=Country), officers=[])";
        assertEquals(expected, company.toString());
    }
}
