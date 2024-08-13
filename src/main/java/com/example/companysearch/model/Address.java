package com.example.companysearch.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {
    private String locality;
    private String postal_code;
    private String premises;
    private String address_line_1;
    private String country;
}
