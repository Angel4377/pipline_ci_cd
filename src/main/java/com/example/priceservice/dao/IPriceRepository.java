package com.example.priceservice.dao;

import com.example.priceservice.entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

    @Repository
    public interface IPriceRepository extends JpaRepository<Price, Long> {

        List<Price> findByProductName(String productName);


}
