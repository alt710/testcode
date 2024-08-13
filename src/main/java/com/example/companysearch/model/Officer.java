package com.example.companysearch.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "officer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Officer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String officerRole;
    private String appointedOn;
    private String resignedOn;
    private String occupation;
    private String countryOfResidence;
    private String nationality;

    @Embedded
    private Address address;

    @Embedded
    private DateOfBirth dateOfBirth;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    // Custom constructor
    public Officer(String name, String officerRole, String appointedOn, String resignedOn, String occupation,
                   String countryOfResidence, String nationality, Address address, DateOfBirth dateOfBirth) {
        this.name = name;
        this.officerRole = officerRole;
        this.appointedOn = appointedOn;
        this.resignedOn = resignedOn;
        this.occupation = occupation;
        this.countryOfResidence = countryOfResidence;
        this.nationality = nationality;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    // Setter to ensure bi-directional relationship is set correctly
    public void setCompany(Company company) {
        this.company = company;
    }
}
