package com.example.companysearch.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class OfficerResponse {
    private String etag;
    private Links links;
    private String kind;
    private int itemsPerPage;
    private List<OfficerItem> items;

    @Data
    @NoArgsConstructor
    public static class Links {
        private String self;
    }

    @Data
    @NoArgsConstructor
    public static class OfficerItem {
        private Address address;
        private String name;
        private String appointed_on;
        private String resigned_on;
        private String officer_role;
        private OfficerLinks links;
        private DateOfBirth date_of_birth;
        private String occupation;
        private String country;
        private String nationality;

        @Data
        @NoArgsConstructor
        public static class OfficerLinks {
            private Appointments appointments;

            @Data
            @NoArgsConstructor
            public static class Appointments {
                private String appointments;
            }
        }

    }
}

