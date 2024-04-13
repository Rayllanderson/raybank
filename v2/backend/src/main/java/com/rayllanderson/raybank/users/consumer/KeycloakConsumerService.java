package com.rayllanderson.raybank.users.consumer;

public interface KeycloakConsumerService {

    void process(KeycloackKafkaRequest request);
    boolean supports(KeycloackKafkaRequest request);
}
