package com.example.companysearch.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "company_search_response")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanySearchResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for persistence

    private int pageNumber;
    private String kind;
    private int totalResults;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "searchResponse")
    private List<CompanyItem> items;
}
