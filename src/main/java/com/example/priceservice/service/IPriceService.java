package com.example.priceservice.service;

import com.example.priceservice.entities.Price;

public interface IPriceService {
    Price getPriceById(Long id);
}
