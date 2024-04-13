package com.rayllanderson.raybank.users.consumer;

import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RegisterEstablishmentKeycloakConsumerService implements KeycloakConsumerService {

    private final RegisterUserService registerUserService;

    @Override
    public void process(KeycloackKafkaRequest request) {
        log.info("received create request from realmId:{} and userId: {}", request.realmId(), request.userId());
        registerUserService.preRegisterEstablishemnt(request.toEstablismentInput());
    }

    @Override
    public boolean supports(KeycloackKafkaRequest request) {
        return request.type().equalsIgnoreCase("CREATE") && request.resourcePath() != null;
    }
}
