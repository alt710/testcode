package com.example.companysearch.repository;

import com.example.companysearch.model.Company;
import com.example.companysearch.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CompanyRepositoryTest {

    @Autowired
    private CompanyRepository companyRepository;

    private Company company1;
    private Company company2;

    @BeforeEach
    void setUp() {
        Address address1 = new Address("Locality1", "PostalCode1", "Premises1", "Address Line 1-1", "Country1");
        Address address2 = new Address("Locality2", "PostalCode2", "Premises2", "Address Line 1-2", "Country2");

        company1 = Company.builder()
                .id(1L)
                .companyNumber("12345678")
                .companyStatus("active")
                .dateOfCreation("2022-01-01")
                .title("Example Company 1")
                .companyType("Private Limited")
                .address(address1)
                .build();

        company2 = Company.builder()
                .id(2L)
                .companyNumber("87654321")
                .companyStatus("dissolved")
                .dateOfCreation("2020-05-15")
                .title("Example Company 2")
                .companyType("Public Limited")
                .address(address2)
                .build();

        companyRepository.save(company1);
        companyRepository.save(company2);
    }

    @Test
    void testFindByCompanyNumber_Found() {
        // When
        Company foundCompany = companyRepository.findByCompanyNumber("12345678");

        // Then
        assertNotNull(foundCompany);
        assertEquals(company1.getId(), foundCompany.getId());
        assertEquals(company1.getCompanyNumber(), foundCompany.getCompanyNumber());
        assertEquals(company1.getCompanyStatus(), foundCompany.getCompanyStatus());
        assertEquals(company1.getDateOfCreation(), foundCompany.getDateOfCreation());
        assertEquals(company1.getTitle(), foundCompany.getTitle());
        assertEquals(company1.getCompanyType(), foundCompany.getCompanyType());
        assertEquals(company1.getAddress(), foundCompany.getAddress());
    }

    @Test
    void testFindByCompanyNumber_NotFound() {
        // When
        Company foundCompany = companyRepository.findByCompanyNumber("99999999");

        // Then
        assertNull(foundCompany);
    }

    @Test
    void testFindByCompanyNumber_MultipleCompanies() {
        // Test first company
        Company foundCompany1 = companyRepository.findByCompanyNumber("12345678");
        assertNotNull(foundCompany1);
        assertEquals(company1.getId(), foundCompany1.getId());

        // Test second company
        Company foundCompany2 = companyRepository.findByCompanyNumber("87654321");
        assertNotNull(foundCompany2);
        assertEquals(company2.getId(), foundCompany2.getId());
    }

    @Test
    void testSaveCompany_WithVeryLongCompanyNumber() {
        // Given
        String longCompanyNumber = "1234567890123456789012345678901234567890"; // 40 characters
        Company companyWithLongNumber = Company.builder()
                .id(5L)
                .companyNumber(longCompanyNumber)
                .companyStatus("active")
                .dateOfCreation("2023-03-10")
                .title("Company with Long Number")
                .companyType("Private Limited")
                .address(new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country"))
                .build();

        // When
        Company savedCompany = companyRepository.save(companyWithLongNumber);

        // Then
        assertNotNull(savedCompany);
        assertEquals(longCompanyNumber, savedCompany.getCompanyNumber());
    }
}
