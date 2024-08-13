package com.example.companysearch.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        Address address = new Address();

        // Then
        assertNotNull(address);
        assertNull(address.getLocality());
        assertNull(address.getPostal_code());
        assertNull(address.getPremises());
        assertNull(address.getAddress_line_1());
        assertNull(address.getCountry());
    }

    @Test
    void testAllArgsConstructor() {
        // Given
        Address address = new Address(
                "Locality",
                "12345",
                "Premises",
                "Address Line 1",
                "Country"
        );

        // Then
        assertNotNull(address);
        assertEquals("Locality", address.getLocality());
        assertEquals("12345", address.getPostal_code());
        assertEquals("Premises", address.getPremises());
        assertEquals("Address Line 1", address.getAddress_line_1());
        assertEquals("Country", address.getCountry());
    }

    @Test
    void testBuilder() {
        // Given
        Address address = Address.builder()
                .locality("Locality")
                .postal_code("12345")
                .premises("Premises")
                .address_line_1("Address Line 1")
                .country("Country")
                .build();

        // Then
        assertNotNull(address);
        assertEquals("Locality", address.getLocality());
        assertEquals("12345", address.getPostal_code());
        assertEquals("Premises", address.getPremises());
        assertEquals("Address Line 1", address.getAddress_line_1());
        assertEquals("Country", address.getCountry());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Address address = new Address();

        // When
        address.setLocality("Locality");
        address.setPostal_code("12345");
        address.setPremises("Premises");
        address.setAddress_line_1("Address Line 1");
        address.setCountry("Country");

        // Then
        assertEquals("Locality", address.getLocality());
        assertEquals("12345", address.getPostal_code());
        assertEquals("Premises", address.getPremises());
        assertEquals("Address Line 1", address.getAddress_line_1());
        assertEquals("Country", address.getCountry());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Address address1 = new Address(
                "Locality",
                "12345",
                "Premises",
                "Address Line 1",
                "Country"
        );

        Address address2 = new Address(
                "Locality",
                "12345",
                "Premises",
                "Address Line 1",
                "Country"
        );

        // Then
        assertEquals(address1, address2);
        assertEquals(address1.hashCode(), address2.hashCode());
    }

    @Test
    void testToString() {
        // Given
        Address address = new Address(
                "Locality",
                "12345",
                "Premises",
                "Address Line 1",
                "Country"
        );

        // Then
        String expected = "Address(locality=Locality, postal_code=12345, premises=Premises, address_line_1=Address Line 1, country=Country)";
        assertEquals(expected, address.toString());
    }
}
