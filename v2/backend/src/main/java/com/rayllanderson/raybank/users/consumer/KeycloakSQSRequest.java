package com.rayllanderson.raybank.users.consumer;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rayllanderson.raybank.users.services.register.RegisterUserInput;
import com.rayllanderson.raybank.users.services.update.UpdateUserInput;

import static com.rayllanderson.raybank.users.services.register.RegisterUtils.getIdFromResourcePath;

public class KeyclockSQSRequest {
    private String realmId;
    private Details details;
    private String type;
    private String userId;
    private String resourcePath;

    public KeyclockSQSRequest(
            @JsonProperty("realmId") String realmId,
            @JsonProperty("details") Details details,
            @JsonAlias({"type", "operationType"}) String type,
            @JsonProperty("userId") String userId,
            @JsonProperty("resourcePath") String resourcePath) {
        this.realmId = realmId;
        this.details = details;
        this.type = type;
        this.userId = userId;
        this.resourcePath = resourcePath;
    }

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

    public UpdateUserInput toUpdateUserInput() {
        return new UpdateUserInput(userId, details.previousFirstName, details.previousLastName, details.updatedFirstName, details.updatedLastName);
    }

    public static class Details {
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String previousFirstName;
        private String previousLastName;
        private String updatedFirstName;
        private String updatedLastName;

        public Details(String firstName, String lastName, String username, String email, String previousFirstName, String previousLastName, String updatedFirstName, String updatedLastName) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.username = username;
            this.email = email;
            this.previousFirstName = previousFirstName;
            this.previousLastName = previousLastName;
            this.updatedFirstName = updatedFirstName;
            this.updatedLastName = updatedLastName;
        }

        public String name() {
            return firstName.concat(" ").concat(lastName);
        }
    }
}
