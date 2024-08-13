package com.example.companysearch.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateOfBirth {

    private Integer birthMonth;
    private Integer birthYear;

}
