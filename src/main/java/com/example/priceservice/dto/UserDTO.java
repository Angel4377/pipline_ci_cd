package com.example.priceservice.dto;

import lombok.*;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UserDTO {
        private Long id;
        private String firstName;
        private String lastName;
        private String email;
        private String role;

}
