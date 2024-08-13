package com.example.companysearch.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "company")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    private Long id; // Primary key for persistence

    private String companyNumber;
    private String companyStatus;
    private String dateOfCreation;
    private String title;
    private String companyType;

    @Embedded
    private Address address;

 /*   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_response_id")
    private CompanySearchResponse searchResponse;*/
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Officer> officers;


}
