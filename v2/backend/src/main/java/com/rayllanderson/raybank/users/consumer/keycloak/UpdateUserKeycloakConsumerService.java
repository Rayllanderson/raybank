package com.rayllanderson.raybank.users.consumer;

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
    public void process(KeycloakSQSRequest request) {
        log.info("received UPDATE_PROFILE request from realmId:{} and userId: {}", request.getRealmId(), request.getUserId());
        updateUserService.update(request.toUpdateUserInput());
    }

    @Override
    public boolean supports(KeycloakSQSRequest request) {
        final var hasUpdatedAnyName = request.getDetails().getUpdatedFirstName() != null || request.getDetails().getUpdatedLastName() != null;
        return request.getType().equalsIgnoreCase("UPDATE_PROFILE") && hasUpdatedAnyName;
    }
}
