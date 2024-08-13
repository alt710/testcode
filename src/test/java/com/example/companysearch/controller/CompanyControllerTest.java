package com.example.companysearch.controller;

import com.example.companysearch.dto.CompanySearchRequest;
import com.example.companysearch.model.Address;
import com.example.companysearch.model.Company;
import com.example.companysearch.model.DateOfBirth;
import com.example.companysearch.model.Officer;
import com.example.companysearch.repository.CompanyRepository;
import com.example.companysearch.repository.OfficerRepository;
import com.example.companysearch.service.TruProxyApiService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CompanyController.class)
class CompanyControllerTest {

    private WireMockServer wireMockServer;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TruProxyApiService truProxyApiService;

    @MockBean
    private CompanyRepository companyRepository;

    @MockBean
    private OfficerRepository officerRepository;

    @BeforeEach
    void setUp() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8080));
        wireMockServer.start();
        WireMock.configureFor("localhost", 8080);
    }

    @AfterEach
    void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void testSearchCompany_Success() throws Exception {
        // Given
        String companyNumber = "12345678";
        Company company = new Company();
        company.setCompanyNumber(companyNumber);
        company.setCompanyStatus("active");
        company.setDateOfCreation("2021-01-01");
        company.setTitle("Example Company");
        company.setCompanyType("Ltd");
        company.setId(1L);
        company.setAddress(new Address("Test Locality", "12345", "1", "Test Street", "Test Country"));

        List<Officer> officers = Collections.singletonList(new Officer(
                "John Doe",
                "director",
                "2021-01-01",
                "2022-01-01",
                "Engineer",
                "Test Country",
                "Nationality",
                new Address("Test Locality", "12345", "1", "Test Street", "Test Country"),
                new DateOfBirth(1980, 1)
        ));

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Search"))
                .withQueryParam("Query", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"items\": [ { \"company_number\": \"" + companyNumber + "\", \"company_status\": \"active\", \"date_of_creation\": \"2021-01-01\", \"title\": \"Example Company\", \"company_type\": \"Ltd\", \"address\": { \"locality\": \"Test Locality\", \"postal_code\": \"12345\", \"premises\": \"1\", \"address_line_1\": \"Test Street\", \"country\": \"Test Country\" } } ] }")));

        wireMockServer.stubFor(get(urlPathEqualTo("/Companies/v1/Officers"))
                .withQueryParam("CompanyNumber", equalTo(companyNumber))
                .withHeader("x-api-key", equalTo("test-api-key"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"items\": [ { \"name\": \"John Doe\", \"officer_role\": \"director\", \"appointed_on\": \"2021-01-01\", \"resigned_on\": \"2022-01-01\", \"occupation\": \"Engineer\", \"country\": \"Test Country\", \"nationality\": \"Nationality\", \"address\": { \"locality\": \"Test Locality\", \"postal_code\": \"12345\", \"premises\": \"1\", \"address_line_1\": \"Test Street\", \"country\": \"Test Country\" }, \"date_of_birth\": { \"day\": 1, \"month\": 1, \"year\": 1980 } } ] }")));

        // Mock companyRepository and officerRepository
        when(companyRepository.findById(companyNumber)).thenReturn(Optional.empty());
        when(truProxyApiService.fetchCompanyDetails(companyNumber)).thenReturn(company);
        when(truProxyApiService.fetchCompanyOfficers(companyNumber)).thenReturn(officers);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("api-key", "uniqueId")
                        .content("{\"companyNumber\":\"" + companyNumber + "\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.companyNumber", is(companyNumber)))
                .andExpect(jsonPath("$.companyStatus", is("active")))
                .andExpect(jsonPath("$.title", is("Example Company")))
                .andExpect(jsonPath("$.address.locality", is("Test Locality")))
                .andExpect(jsonPath("$.address.address_line_1", is("Test Street")))
                .andExpect(jsonPath("$.officers[0].name", is("John Doe")))
                .andExpect(jsonPath("$.officers[0].address.locality", is("Test Locality")));
    }

    @Test
    void testSearchCompany_InvalidApiKey() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("api-key", "invalidApiKey")
                        .content("{\"companyNumber\":\"12345678\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid API key"));
    }

    @Test
    void testSearchCompany_InvalidCompanyNumber() throws Exception {
        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("api-key", "uniqueId")
                        .content("{\"companyNumber\":\"\"}"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid Company number"));
    }

    @Test
    void testSearchCompany_CompanyNotFound() throws Exception {
        // Given
        String companyNumber = "12345678";

        // Mock companyRepository and truProxyApiService
        when(companyRepository.findById(companyNumber)).thenReturn(Optional.empty());
        when(truProxyApiService.fetchCompanyDetails(companyNumber)).thenReturn(null);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("api-key", "uniqueId")
                        .content("{\"companyNumber\":\"" + companyNumber + "\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testSearchCompany_CompanyInactive() throws Exception {
        // Given
        String companyNumber = "12345678";
        Company company = new Company();
        company.setCompanyNumber(companyNumber);
        company.setCompanyStatus("inactive");

        // Mock companyRepository and truProxyApiService
        when(companyRepository.findById(companyNumber)).thenReturn(Optional.empty());
        when(truProxyApiService.fetchCompanyDetails(companyNumber)).thenReturn(company);

        // When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/companies/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("api-key", "uniqueId")
                        .content("{\"companyNumber\":\"" + companyNumber + "\"}"))
                .andExpect(status().isNoContent());
    }

}
