package com.rayllanderson.raybank.users.consumer;

import com.rayllanderson.raybank.users.services.register.RegisterUserService;
import com.rayllanderson.raybank.users.services.update.UpdateUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateUserKeycloakConsumerService implements KeycloakConsumerService {

    private final UpdateUserService updateUserService;

    @Override
    public void process(KeycloackKafkaRequest request) {
        log.info("received UPDATE_PROFILE request from realmId:{} and userId: {}", request.realmId(), request.userId());
        updateUserService.update(request.toUpdateUserInput());
    }

    @Override
    public boolean supports(KeycloackKafkaRequest request) {
        final var hasUpdatedAnyName = request.details().updatedFirstName() != null || request.details().updatedLastName() != null;
        return request.type().equalsIgnoreCase("UPDATE_PROFILE") && hasUpdatedAnyName;
    }
}
