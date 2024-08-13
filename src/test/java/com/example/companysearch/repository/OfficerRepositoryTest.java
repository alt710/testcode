package com.example.companysearch.repository;

import com.example.companysearch.model.Officer;
import com.example.companysearch.model.Company;
import com.example.companysearch.model.Address;
import com.example.companysearch.model.DateOfBirth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OfficerRepositoryTest {

    @Autowired
    private OfficerRepository officerRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company company1;
    private Officer officer1;
    private Officer officer2;

    @BeforeEach
    void setUp() {
        Address address = new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country");
        DateOfBirth dob = new DateOfBirth(1980, 01);

        company1 = Company.builder()
                .id(1L)
                .companyNumber("12345678")
                .companyStatus("active")
                .dateOfCreation("2022-01-01")
                .title("Example Company")
                .companyType("Private Limited")
                .address(address)
                .build();

        companyRepository.save(company1);

        officer1 = new Officer("John Doe", "Director", "2022-02-01", null, "Software Engineer",
                "USA", "American", address, dob);
        officer1.setCompany(company1);

        officer2 = new Officer("Jane Smith", "Secretary", "2022-03-01", null, "Accountant",
                "USA", "American", address, dob);
        officer2.setCompany(company1);

        officerRepository.save(officer1);
        officerRepository.save(officer2);
    }

    @Test
    void testFindByCompanyId_Found() {
        // When
        List<Officer> officers = officerRepository.findByCompanyId(company1.getId());

        // Then
        assertNotNull(officers);
        assertEquals(2, officers.size());
        assertTrue(officers.contains(officer1));
        assertTrue(officers.contains(officer2));
    }

    @Test
    void testFindByCompanyId_NotFound() {
        // When
        List<Officer> officers = officerRepository.findByCompanyId(999L);

        // Then
        assertNotNull(officers);
        assertTrue(officers.isEmpty());
    }

    @Test
    void testSaveMultipleOfficersForCompany() {
        // Given
        Address address = new Address("New Locality", "New PostalCode", "New Premises", "New Address Line 1", "New Country");
        DateOfBirth dob = new DateOfBirth(1975, 05);

        Officer officer3 = new Officer("Alice Johnson", "CEO", "2023-01-01", null, "Manager",
                "Canada", "Canadian", address, dob);
        officer3.setCompany(company1);

        // When
        officerRepository.save(officer3);

        List<Officer> officers = officerRepository.findByCompanyId(company1.getId());

        // Then
        assertNotNull(officers);
        assertEquals(3, officers.size());
        assertTrue(officers.contains(officer3));
    }

    @Test
    void testDeleteOfficer() {
        // When
        officerRepository.delete(officer1);

        List<Officer> officers = officerRepository.findByCompanyId(company1.getId());

        // Then
        assertNotNull(officers);
        assertEquals(1, officers.size());
        assertFalse(officers.contains(officer1));
    }

    @Test
    void testFindByCompanyId_NullCompanyId() {
        // When
        List<Officer> officers = officerRepository.findByCompanyId(null);

        // Then
        assertNotNull(officers);
        assertTrue(officers.isEmpty());
    }

    @Test
    void testFindByCompanyId_InvalidCompanyId() {
        // Given
        long invalidCompanyId = -1L;

        // When
        List<Officer> officers = officerRepository.findByCompanyId(invalidCompanyId);

        // Then
        assertNotNull(officers);
        assertTrue(officers.isEmpty());
    }
}
