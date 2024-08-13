package com.example.companysearch.controller;

import com.example.companysearch.dto.CompanySearchRequest;
import com.example.companysearch.model.Company;
import com.example.companysearch.model.Officer;
import com.example.companysearch.repository.OfficerRepository;
import com.example.companysearch.service.TruProxyApiService;
import com.example.companysearch.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {


    @Autowired
    private TruProxyApiService truProxyApiService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private OfficerRepository officerRepository;

    // Predefined API key for authentication (this should be stored securely, e.g., in environment variables)
    private static final String VALID_API_KEY = "uniqueId";

    @PostMapping("/search")
    public ResponseEntity<?> searchCompany(
            @RequestBody CompanySearchRequest request,
            @RequestHeader("api-key") String apiKey,
            @RequestParam(value = "onlyActive", required = false, defaultValue = "true") boolean onlyActive) {
        // Authenticate the api-key
        if (!VALID_API_KEY.equals(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        if(request.getCompanyNumber() == null || request.getCompanyNumber().isEmpty() ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Company number");
        }

        Optional<Company> companyOpt = companyRepository.findById(request.getCompanyNumber());
        if (companyOpt.isPresent()) {
            Company company = companyOpt.get();
            return ResponseEntity.ok(companyOpt.get());
        }

        Company company = truProxyApiService.fetchCompanyDetails(request.getCompanyNumber());
        if (company == null || (onlyActive && !"active".equalsIgnoreCase(company.getCompanyStatus()))) {
            return ResponseEntity.noContent().build();
        }
        List<Officer> list = truProxyApiService.fetchCompanyOfficers(request.getCompanyNumber());
        company.setOfficers(list);
        companyRepository.save(company);
        officerRepository.saveAll(list);
        return ResponseEntity.ok(company);
    }

}
