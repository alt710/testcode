package com.example.companysearch.service;

import com.example.companysearch.exception.CompanyNotFoundException;
import com.example.companysearch.model.Company;
import com.example.companysearch.model.Officer;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class TruProxyApiServiceTest {

    private WireMockServer wireMockServer;

    private TruProxyApiService truProxyApiService;

    @BeforeEach
    void setUp() {
        // Initialize WireMock
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);

        // Initialize the service with the mocked URL and API key
        truProxyApiService = new TruProxyApiService();
        truProxyApiService.setTruProxyApiUrl("http://localhost:8080");
        truProxyApiService.setTruProxyApiKey("test-api-key");
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testFetchCompanyDetails_Success() {
        // Given
        String companyNumber = "12345678";
        String responseBody = "{ \"items\": [ { \"company_number\": \"" + companyNumber + "\", \"company_status\": \"active\" } ] }";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Search"))
                .withQueryParam("Query", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        // When
        Company company = truProxyApiService.fetchCompanyDetails(companyNumber);

        // Then
        assertNotNull(company);
        assertEquals(companyNumber, company.getCompanyNumber());
        assertEquals("active", company.getCompanyStatus());
    }

    @Test
    void testFetchCompanyDetails_NotFound() {
        // Given
        String companyNumber = "00000000";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Search"))
                .withQueryParam("Query", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND.value())));

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> {
            truProxyApiService.fetchCompanyDetails(companyNumber);
        });
    }

    @Test
    void testFetchCompanyDetails_ServerError() {
        // Given
        String companyNumber = "12345678";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Search"))
                .withQueryParam("Query", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> {
            truProxyApiService.fetchCompanyDetails(companyNumber);
        });
    }

    @Test
    void testFetchCompanyOfficers_Success() {
        // Given
        String companyNumber = "12345678";
        String responseBody = "{ \"items\": [ { \"name\": \"John Doe\", \"officer_role\": \"director\" } ] }";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Officers"))
                .withQueryParam("CompanyNumber", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        // When
        List<Officer> officers = truProxyApiService.fetchCompanyOfficers(companyNumber);

        // Then
        assertNotNull(officers);
        assertEquals(1, officers.size());
        assertEquals("John Doe", officers.get(0).getName());
        assertEquals("director", officers.get(0).getOfficerRole());
    }

    @Test
    void testFetchCompanyOfficers_EmptyResponse() {
        // Given
        String companyNumber = "12345678";
        String responseBody = "{ \"items\": [] }";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Officers"))
                .withQueryParam("CompanyNumber", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        // When
        List<Officer> officers = truProxyApiService.fetchCompanyOfficers(companyNumber);

        // Then
        assertNotNull(officers);
        assertTrue(officers.isEmpty());
    }

    @Test
    void testFetchCompanyOfficers_ServerError() {
        // Given
        String companyNumber = "12345678";

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Officers"))
                .withQueryParam("CompanyNumber", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())));

        // When & Then
        assertThrows(CompanyNotFoundException.class, () -> {
            truProxyApiService.fetchCompanyOfficers(companyNumber);
        });
    }
}
