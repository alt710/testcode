package com.example.companysearch.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CompanyItemTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        CompanyItem companyItem = new CompanyItem();

        // Then
        assertNotNull(companyItem);
        assertNull(companyItem.getId());
        assertNull(companyItem.getCompany_number());
        assertNull(companyItem.getCompany_status());
        assertNull(companyItem.getDate_of_creation());
        assertNull(companyItem.getTitle());
        assertNull(companyItem.getCompany_type());
        assertNull(companyItem.getAddress());
        assertNull(companyItem.getSearchResponse());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Address address = new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country");
        CompanySearchResponse searchResponse = new CompanySearchResponse();
        CompanyItem companyItem = new CompanyItem(
                1L,
                "12345678",
                "active",
                "2022-01-01",
                "Example Company",
                "Private Limited",
                address,
                searchResponse
        );

        // Then
        assertEquals(1L, companyItem.getId());
        assertEquals("12345678", companyItem.getCompany_number());
        assertEquals("active", companyItem.getCompany_status());
        assertEquals("2022-01-01", companyItem.getDate_of_creation());
        assertEquals("Example Company", companyItem.getTitle());
        assertEquals("Private Limited", companyItem.getCompany_type());
        assertEquals(address, companyItem.getAddress());
        assertEquals(searchResponse, companyItem.getSearchResponse());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        CompanyItem companyItem = new CompanyItem();
        Address address = new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country");
        CompanySearchResponse searchResponse = new CompanySearchResponse();

        // When
        companyItem.setId(1L);
        companyItem.setCompany_number("12345678");
        companyItem.setCompany_status("active");
        companyItem.setDate_of_creation("2022-01-01");
        companyItem.setTitle("Example Company");
        companyItem.setCompany_type("Private Limited");
        companyItem.setAddress(address);
        companyItem.setSearchResponse(searchResponse);

        // Then
        assertEquals(1L, companyItem.getId());
        assertEquals("12345678", companyItem.getCompany_number());
        assertEquals("active", companyItem.getCompany_status());
        assertEquals("2022-01-01", companyItem.getDate_of_creation());
        assertEquals("Example Company", companyItem.getTitle());
        assertEquals("Private Limited", companyItem.getCompany_type());
        assertEquals(address, companyItem.getAddress());
        assertEquals(searchResponse, companyItem.getSearchResponse());
    }

    @Test
    void testToString() {
        // Given
        Address address = new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country");
        CompanyItem companyItem = CompanyItem.builder()
                .id(1L)
                .company_number("12345678")
                .company_status("active")
                .date_of_creation("2022-01-01")
                .title("Example Company")
                .company_type("Private Limited")
                .address(address)
                .build();

        // Then
        String expected = "CompanyItem(id=1, company_number=12345678, company_status=active, date_of_creation=2022-01-01, title=Example Company, company_type=Private Limited, address=Address(locality=Locality, postal_code=PostalCode, premises=Premises, address_line_1=Address Line 1, country=Country), searchResponse=null)";
        assertEquals(expected, companyItem.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        CompanyItem companyItem1 = CompanyItem.builder()
                .id(1L)
                .company_number("12345678")
                .company_status("active")
                .date_of_creation("2022-01-01")
                .title("Example Company")
                .company_type("Private Limited")
                .build();

        CompanyItem companyItem2 = CompanyItem.builder()
                .id(1L)
                .company_number("12345678")
                .company_status("active")
                .date_of_creation("2022-01-01")
                .title("Example Company")
                .company_type("Private Limited")
                .build();

        // Then
        assertEquals(companyItem1, companyItem2);
        assertEquals(companyItem1.hashCode(), companyItem2.hashCode());
    }
}
