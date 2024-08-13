package com.example.companysearch.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OfficerTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        Officer officer = new Officer();

        // Then
        assertNotNull(officer);
        assertNull(officer.getId());
        assertNull(officer.getName());
        assertNull(officer.getOfficerRole());
        assertNull(officer.getAppointedOn());
        assertNull(officer.getResignedOn());
        assertNull(officer.getOccupation());
        assertNull(officer.getCountryOfResidence());
        assertNull(officer.getNationality());
        assertNull(officer.getAddress());
        assertNull(officer.getDateOfBirth());
        assertNull(officer.getCompany());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        DateOfBirth dob = new DateOfBirth(1990, 5);
        Company company = new Company();
        Officer officer = new Officer(
                "John Doe",
                "Director",
                "2022-01-01",
                "2023-01-01",
                "Engineer",
                "UK",
                "British",
                address,
                dob
        );

        // Then
        assertNotNull(officer);
        assertEquals("John Doe", officer.getName());
        assertEquals("Director", officer.getOfficerRole());
        assertEquals("2022-01-01", officer.getAppointedOn());
        assertEquals("2023-01-01", officer.getResignedOn());
        assertEquals("Engineer", officer.getOccupation());
        assertEquals("UK", officer.getCountryOfResidence());
        assertEquals("British", officer.getNationality());
        assertEquals(address, officer.getAddress());
        assertEquals(dob, officer.getDateOfBirth());
        assertNull(officer.getCompany());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Officer officer = new Officer();
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        DateOfBirth dob = new DateOfBirth(1990, 5);
        Company company = new Company();

        // When
        officer.setName("John Doe");
        officer.setOfficerRole("Director");
        officer.setAppointedOn("2022-01-01");
        officer.setResignedOn("2023-01-01");
        officer.setOccupation("Engineer");
        officer.setCountryOfResidence("UK");
        officer.setNationality("British");
        officer.setAddress(address);
        officer.setDateOfBirth(dob);
        officer.setCompany(company);

        // Then
        assertEquals("John Doe", officer.getName());
        assertEquals("Director", officer.getOfficerRole());
        assertEquals("2022-01-01", officer.getAppointedOn());
        assertEquals("2023-01-01", officer.getResignedOn());
        assertEquals("Engineer", officer.getOccupation());
        assertEquals("UK", officer.getCountryOfResidence());
        assertEquals("British", officer.getNationality());
        assertEquals(address, officer.getAddress());
        assertEquals(dob, officer.getDateOfBirth());
        assertEquals(company, officer.getCompany());
    }

    @Test
    void testSetCompany() {
        // Given
        Officer officer = new Officer();
        Company company = new Company();

        // When
        officer.setCompany(company);

        // Then
        assertEquals(company, officer.getCompany());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Address address = new Address("Locality", "12345", "Premises", "Address Line 1", "Country");
        DateOfBirth dob = new DateOfBirth(1990, 5);
        Officer officer1 = new Officer(
                "John Doe",
                "Director",
                "2022-01-01",
                "2023-01-01",
                "Engineer",
                "UK",
                "British",
                address,
                dob
        );

        Officer officer2 = new Officer(
                "John Doe",
                "Director",
                "2022-01-01",
                "2023-01-01",
                "Engineer",
                "UK",
                "British",
                address,
                dob
        );

        // Then
        assertEquals(officer1, officer2);
        assertEquals(officer1.hashCode(), officer2.hashCode());
    }

}
