package com.rayllanderson.raybank.users.consumer.keycloak;

public interface KeycloakConsumerService {

    void process(KeycloakSQSRequest request);
    boolean supports(KeycloakSQSRequest request);
}
