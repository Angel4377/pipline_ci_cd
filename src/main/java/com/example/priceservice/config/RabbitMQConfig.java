package com.example.priceservice.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class RabbitMQConfig {
        public static final String QUEUE = "report-queue";
        public static final String EXCHANGE = "report-exchange";
        public static final String ROUTING_KEY = "report-routing-key";

        @Bean
        public Queue reportQueue() {
            return new Queue(QUEUE, true); // durable
        }

        @Bean
        public org.springframework.amqp.core.Exchange reportExchange() {
            return ExchangeBuilder.directExchange(EXCHANGE).durable(true).build();
        }

        @Bean
        public Binding binding(Queue reportQueue, org.springframework.amqp.core.Exchange reportExchange) {
            return BindingBuilder.bind(reportQueue).to(reportExchange).with(ROUTING_KEY).noargs();
        }
            @Bean
            public Jackson2JsonMessageConverter jsonMessageConverter() {
                return new Jackson2JsonMessageConverter();
            }

}
