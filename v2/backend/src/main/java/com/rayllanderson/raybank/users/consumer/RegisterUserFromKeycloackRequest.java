package com.rayllanderson.raybank.users.consumer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rayllanderson.raybank.users.services.register.RegisterUserInput;

import static com.rayllanderson.raybank.users.services.register.RegisterUtils.getIdFromResourcePath;

public record RegisterUserFromKeycloackRequest(
        @JsonProperty("realmId")
        String realmId,
        Details details,
        String type,
        @JsonProperty("userId")
        String userId,
        @JsonProperty("resourcePath")
        String resourcePath
) {

    public RegisterUserInput toUserInput() {
        final var input = new RegisterUserInput();
        input.setId(userId);
        input.setName(details.name());
        input.setUsername(details.username());
        input.setEmail(details.email());
        input.setRegisterType(RegisterUserInput.RegisterType.USER);
        return input;
    }

    public RegisterUserInput toEstablismentInput() {
        final var input = new RegisterUserInput();
        input.setId(getIdFromResourcePath(resourcePath));
        input.setRegisterType(RegisterUserInput.RegisterType.ESTABLISHMENT);
        return input;
    }


    record Details(
            String firstName,
            String lastName,
            String username,
            String email
    ) {
        String name() {
            return firstName.concat(" ").concat(lastName);
        }
    }
}
