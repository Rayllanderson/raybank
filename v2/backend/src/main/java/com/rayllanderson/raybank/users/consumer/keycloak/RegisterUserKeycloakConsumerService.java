package com.rayllanderson.raybank.users.consumer;

import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterUserKeycloakConsumerService implements KeycloakConsumerService {

    private final RegisterUserService registerUserService;

    @Override
    public void process(KeycloakSQSRequest request) {
        log.info("received register request from realmId:{} and userId: {}", request.getRealmId(), request.getUserId());
        registerUserService.register(request.toUserInput());
    }

    @Override
    public boolean supports(KeycloakSQSRequest request) {
        return request.getType().equalsIgnoreCase("REGISTER");
    }
}
