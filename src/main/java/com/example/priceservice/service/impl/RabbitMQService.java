package com.example.priceservice.service.impl;

import com.example.priceservice.service.IRabbitMQService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQService implements IRabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    @Value("${spring.rabbitmq.routingkey}")
    private String routingKey;

    @Override
    public void sendPriceAlert(Object message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}