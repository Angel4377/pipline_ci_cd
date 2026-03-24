package com.example.priceservice.dto;


import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class PriceDTO {

        private Long id;

        private String productName;   // nom du produit

        private BigDecimal price;     // prix du produit

        private Long userId;          // utilisateur qui a rapporté le prix

        private String sellerName;    // magasin ou vendeur

        private String location;      // localisation

        private String source;        // CONTROLLER ou CROWDSOURCE

        private Boolean verified;     // prix validé ou non

        private LocalDateTime collectedDate;

}
