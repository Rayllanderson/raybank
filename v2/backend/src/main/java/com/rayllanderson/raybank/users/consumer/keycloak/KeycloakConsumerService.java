package com.rayllanderson.raybank.users.consumer;

public interface KeycloakConsumerService {

    void process(KeycloakSQSRequest request);
    boolean supports(KeycloakSQSRequest request);
}
