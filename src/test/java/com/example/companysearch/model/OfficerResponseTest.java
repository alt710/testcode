package com.example.companysearch.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OfficerResponseTest {

    @Test
    void testNoArgsConstructor() {
        // Given
        OfficerResponse officerResponse = new OfficerResponse();

        // Then
        assertNotNull(officerResponse);
        assertNull(officerResponse.getEtag());
        assertNull(officerResponse.getLinks());
        assertNull(officerResponse.getKind());
        assertEquals(0, officerResponse.getItemsPerPage());
        assertNull(officerResponse.getItems());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        OfficerResponse officerResponse = new OfficerResponse();
        OfficerResponse.Links links = new OfficerResponse.Links();
        links.setSelf("self-link");

        List<OfficerResponse.OfficerItem> officerItems = new ArrayList<>();
        OfficerResponse.OfficerItem officerItem = new OfficerResponse.OfficerItem();
        officerItem.setName("John Doe");
        officerItems.add(officerItem);

        // When
        officerResponse.setEtag("etag");
        officerResponse.setLinks(links);
        officerResponse.setKind("kind");
        officerResponse.setItemsPerPage(10);
        officerResponse.setItems(officerItems);

        // Then
        assertEquals("etag", officerResponse.getEtag());
        assertEquals("self-link", officerResponse.getLinks().getSelf());
        assertEquals("kind", officerResponse.getKind());
        assertEquals(10, officerResponse.getItemsPerPage());
        assertEquals(1, officerResponse.getItems().size());
        assertEquals("John Doe", officerResponse.getItems().get(0).getName());
    }

    @Test
    void testOfficerItemNoArgsConstructor() {
        // Given
        OfficerResponse.OfficerItem officerItem = new OfficerResponse.OfficerItem();

        // Then
        assertNotNull(officerItem);
        assertNull(officerItem.getName());
        assertNull(officerItem.getAddress());
        assertNull(officerItem.getAppointed_on());
        assertNull(officerItem.getResigned_on());
        assertNull(officerItem.getOfficer_role());
        assertNull(officerItem.getLinks());
        assertNull(officerItem.getDate_of_birth());
        assertNull(officerItem.getOccupation());
        assertNull(officerItem.getCountry());
        assertNull(officerItem.getNationality());
    }

    @Test
    void testOfficerItemSettersAndGetters() {
        // Given
        OfficerResponse.OfficerItem officerItem = new OfficerResponse.OfficerItem();
        OfficerResponse.OfficerItem.OfficerLinks officerLinks = new OfficerResponse.OfficerItem.OfficerLinks();
        OfficerResponse.OfficerItem.OfficerLinks.Appointments appointments = new OfficerResponse.OfficerItem.OfficerLinks.Appointments();
        appointments.setAppointments("appointments-link");
        officerLinks.setAppointments(appointments);

        Address address = new Address("Locality", "PostalCode", "Premises", "Address Line 1", "Country");
        DateOfBirth dateOfBirth = new DateOfBirth(1980, 6);

        // When
        officerItem.setName("Jane Doe");
        officerItem.setAddress(address);
        officerItem.setAppointed_on("2022-01-01");
        officerItem.setResigned_on("2023-01-01");
        officerItem.setOfficer_role("Director");
        officerItem.setLinks(officerLinks);
        officerItem.setDate_of_birth(dateOfBirth);
        officerItem.setOccupation("Engineer");
        officerItem.setCountry("UK");
        officerItem.setNationality("British");

        // Then
        assertEquals("Jane Doe", officerItem.getName());
        assertEquals(address, officerItem.getAddress());
        assertEquals("2022-01-01", officerItem.getAppointed_on());
        assertEquals("2023-01-01", officerItem.getResigned_on());
        assertEquals("Director", officerItem.getOfficer_role());
        assertEquals("appointments-link", officerItem.getLinks().getAppointments().getAppointments());
        assertEquals(dateOfBirth, officerItem.getDate_of_birth());
        assertEquals("Engineer", officerItem.getOccupation());
        assertEquals("UK", officerItem.getCountry());
        assertEquals("British", officerItem.getNationality());
    }

    @Test
    void testLinksNoArgsConstructor() {
        // Given
        OfficerResponse.Links links = new OfficerResponse.Links();

        // Then
        assertNotNull(links);
        assertNull(links.getSelf());
    }

    @Test
    void testOfficerLinksNoArgsConstructor() {
        // Given
        OfficerResponse.OfficerItem.OfficerLinks officerLinks = new OfficerResponse.OfficerItem.OfficerLinks();

        // Then
        assertNotNull(officerLinks);
        assertNull(officerLinks.getAppointments());
    }

    @Test
    void testAppointmentsNoArgsConstructor() {
        // Given
        OfficerResponse.OfficerItem.OfficerLinks.Appointments appointments = new OfficerResponse.OfficerItem.OfficerLinks.Appointments();

        // Then
        assertNotNull(appointments);
        assertNull(appointments.getAppointments());
    }

    @Test
    void testToString() {
        // Given
        OfficerResponse officerResponse = new OfficerResponse();
        officerResponse.setEtag("etag");
        OfficerResponse.Links links = new OfficerResponse.Links();
        links.setSelf("self-link");
        officerResponse.setLinks(links);
        officerResponse.setKind("kind");

        OfficerResponse.OfficerItem officerItem = new OfficerResponse.OfficerItem();
        officerItem.setName("John Doe");

        List<OfficerResponse.OfficerItem> officerItems = new ArrayList<>();
        officerItems.add(officerItem);
        officerResponse.setItems(officerItems);

        // Then
        String expected = "OfficerResponse(etag=etag, links=OfficerResponse.Links(self=self-link), kind=kind, itemsPerPage=0, items=[OfficerResponse.OfficerItem(address=null, name=John Doe, appointed_on=null, resigned_on=null, officer_role=null, links=null, date_of_birth=null, occupation=null, country=null, nationality=null)])";
        assertEquals(expected, officerResponse.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        OfficerResponse officerResponse1 = new OfficerResponse();
        officerResponse1.setEtag("etag");

        OfficerResponse officerResponse2 = new OfficerResponse();
        officerResponse2.setEtag("etag");

        // Then
        assertEquals(officerResponse1, officerResponse2);
        assertEquals(officerResponse1.hashCode(), officerResponse2.hashCode());
    }
}
