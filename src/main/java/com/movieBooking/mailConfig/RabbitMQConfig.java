package com.movieBooking.mailConfig;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Define booking and cancel booking queues
    @Bean
    public Queue bookingQueue() {
        return new Queue("booking-queue", true); // Durable queue
    }

    @Bean
    public Queue cancelBookingQueue() {
        return new Queue("cancelbooking-queue", true); // Durable queue
    }

    // Define a topic exchange for booking messages
    @Bean
    public TopicExchange bookingExchange() {
        return new TopicExchange("booking-exchange");
    }

    // Bind queues to exchange with routing keys
    @Bean
    public Binding bookingBinding(Queue bookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(bookingQueue).to(bookingExchange).with("booking.key");
    }

    @Bean
    public Binding cancelBookingBinding(Queue cancelBookingQueue, TopicExchange bookingExchange) {
        return BindingBuilder.bind(cancelBookingQueue).to(bookingExchange).with("cancelbooking.key");
    }
}
