package com.example.companysearch.repository;

import com.example.companysearch.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
    Company findByCompanyNumber(String companyNumber);
}



