package com.rayllanderson.raybank.users.consumer.keycloak;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rayllanderson.raybank.users.services.register.RegisterUserInput;
import com.rayllanderson.raybank.users.services.update.UpdateUserInput;
import lombok.Getter;
import lombok.Setter;

import static com.rayllanderson.raybank.users.services.register.RegisterUtils.getIdFromResourcePath;

@Getter
@Setter
public class KeycloakSQSRequest {
    @JsonProperty("realmId")
    private String realmId;
    private Details details;
    @JsonAlias({"type", "operationType"})
    private String type;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("resourcePath")
    private String resourcePath;

    @Getter
    @Setter
    public static class Details {
        private String firstName;
        private String lastName;
        private String username;
        private String email;
        private String previousFirstName;
        private String previousLastName;
        private String updatedFirstName;
        private String updatedLastName;

        public String name() {
            return firstName.concat(" ").concat(lastName);
        }
    }

    public RegisterUserInput toUserInput() {
        final var input = new RegisterUserInput();
        input.setId(userId);
        input.setName(details.name());
        input.setUsername(details.username);
        input.setEmail(details.email);
        input.setRegisterType(RegisterUserInput.RegisterType.USER);
        return input;
    }

    public RegisterUserInput toEstablishmentInput() {
        final var input = new RegisterUserInput();
        input.setId(getIdFromResourcePath(resourcePath));
        input.setRegisterType(RegisterUserInput.RegisterType.ESTABLISHMENT);
        return input;
    }

    public UpdateUserInput toUpdateUserInput() {
        return new UpdateUserInput(userId, details.previousFirstName, details.previousLastName, details.updatedFirstName, details.updatedLastName);
    }
}
