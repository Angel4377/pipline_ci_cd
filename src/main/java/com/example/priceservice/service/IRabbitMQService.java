package com.example.priceservice.service;

public interface IRabbitMQService {
    void sendPriceAlert(Object message);

}
