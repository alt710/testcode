package com.example.companysearch.repository;

import com.example.companysearch.model.Officer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficerRepository extends JpaRepository<Officer, Long> {

    List<Officer> findByCompanyId(Long companyId);
}