package com.example.companysearch.service;

import com.example.companysearch.exception.CompanyNotFoundException;
import com.example.companysearch.model.Company;
import com.example.companysearch.model.Officer;
import com.example.companysearch.model.CompanyItem;
import com.example.companysearch.model.CompanySearchResponse;
import com.example.companysearch.model.OfficerResponse;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TruProxyApiService {

    private static final Logger logger = LoggerFactory.getLogger(TruProxyApiService.class);

    @Setter
    @Value("${truproxy.api.url}")
    private String truProxyApiUrl;

    @Setter
    @Value("${truproxy.api.key}")
    private String truProxyApiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Company fetchCompanyDetails(String companyNumber) {
        String url = truProxyApiUrl + "/Companies/v1/Search?Query=" + companyNumber;


        return executeApiCall(
                url,
                CompanySearchResponse.class,
                response -> {
                    if (response != null && response.getItems() != null && !response.getItems().isEmpty()) {
                        CompanyItem item = response.getItems().getFirst();

                        if (item != null && companyNumber.equals(item.getCompany_number())) {
                            // Map the fields from CompanyItem to Company
                            Company company = new Company();
                            company.setId(Long.valueOf(item.getCompany_number()));
                            company.setCompanyNumber(item.getCompany_number());
                            company.setCompanyStatus(item.getCompany_status());
                            company.setDateOfCreation(item.getDate_of_creation());
                            company.setTitle(item.getTitle());
                            company.setCompanyType(item.getCompany_type());
                            company.setAddress(item.getAddress());
                            return company;
                        }
                    }
                    throw new CompanyNotFoundException("Company details not found for company number: " + companyNumber);
                }
        );
    }


    public List<Officer> fetchCompanyOfficers(String companyNumber) {
        String url = truProxyApiUrl + "/Companies/v1/Officers?CompanyNumber=" + companyNumber;

        return executeApiCall(
                url,
                OfficerResponse.class,
                officerResponse -> {
                    if (officerResponse != null && officerResponse.getItems() != null && !officerResponse.getItems().isEmpty()) {
                        return officerResponse.getItems().stream()
                                .map(item -> new Officer(
                                        item.getName(),
                                        item.getOfficer_role(),
                                        item.getAppointed_on(),
                                        item.getResigned_on(),
                                        item.getOccupation(),
                                        item.getCountry(),
                                        item.getNationality(),
                                        item.getAddress(),
                                        item.getDate_of_birth()
                                ))
                                .collect(Collectors.toList());
                    } else {
                        logger.warn("No officers found or officers list is empty for company number: {}", companyNumber);
                        return Collections.emptyList();
                    }
                }
        );
    }



    private <T, R> R executeApiCall(String url, Class<T> responseType, ApiResponseHandler<T, R> responseHandler) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("x-api-key", truProxyApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);

            if (response.getStatusCode() == HttpStatus.OK) {
                return responseHandler.handle(response.getBody());
            } else {
                logAndThrow("Unexpected status code: " + response.getStatusCode(), url);
                return null;  // This line is never reached due to exception throw
            }
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            logAndThrow("Error occurred while calling API: " + ex.getMessage(), url, ex);
            return null;  // This line is never reached due to exception throw
        } catch (RestClientException ex) {
            logAndThrow("Connection error occurred: " + ex.getMessage(), url, ex);
            return null;  // This line is never reached due to exception throw
        }
    }

    private void logAndThrow(String message, String url) {
        logger.error("API call failed to {}: {}", url, message);
        throw new CompanyNotFoundException(message);
    }

    private void logAndThrow(String message, String url, Exception ex) {
        logger.error("API call failed to {}: {}", url, message, ex);
        throw new CompanyNotFoundException(message, ex);
    }

    @FunctionalInterface
    private interface ApiResponseHandler<T, R> {
        R handle(T response);
    }
}

