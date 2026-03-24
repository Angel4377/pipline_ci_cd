package com.example.priceservice.entities;




import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

    @Entity
    @Table(name = "prices")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Price {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String productName;  // Ex: "Riz brisé"

        @Column(nullable = false, precision = 10, scale = 2)
        private BigDecimal price;    // Ex: 5000 FCFA

        @Column(nullable = false)
        private Long userId;         // ID de l'utilisateur qui a rapporté le prix

        @Column(nullable = false)
        private String sellerName;   // Ex: "Auchan Dakar"

        private String location;     // Ex: "Dakar, Plateau"

        @Enumerated(EnumType.STRING)
        private PriceSource source;  // CONTROLLER ou CROWDSOURCE

        @Column(name = "collected_date")
        private LocalDateTime collectedDate;

        private Boolean verified = false;

        public enum PriceSource {
            CONTROLLER,    // Relevé par un agent DCCRF
            CROWDSOURCE    // Rapporté par un citoyen
        }

}
