package com.example.companysearch.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Primary key for persistence
    @Setter
    @Getter
    private String company_number;
    @Setter
    @Getter
    private String company_status;
    @Setter
    @Getter
    private String date_of_creation;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String company_type;
    @Setter
    @Getter
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "search_response_id")
    private CompanySearchResponse searchResponse;

}
