package com.example.priceservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        return template;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("regulation-exchange");
    }

    @Bean
    public Queue priceQueue() {
        return new Queue("price-queue");
    }

    @Bean
    public Queue reportQueue() {
        return new Queue("report-queue");
    }

    @Bean
    public Queue userQueue() {
        return new Queue("user-queue");
    }

    @Bean
    public Binding priceBinding() {
        return BindingBuilder.bind(priceQueue())
                .to(exchange())
                .with("price-routing-key");
    }

    @Bean
    public Binding reportBinding() {
        return BindingBuilder.bind(reportQueue())
                .to(exchange())
                .with("report-routing-key");
    }

    @Bean
    public Binding userBinding() {
        return BindingBuilder.bind(userQueue())
                .to(exchange())
                .with("user-routing-key");
    }
}
