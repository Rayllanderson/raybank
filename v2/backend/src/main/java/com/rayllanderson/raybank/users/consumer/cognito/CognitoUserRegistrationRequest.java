package com.rayllanderson.raybank.users.consumer.cognito;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rayllanderson.raybank.users.services.register.RegisterUserInput;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CognitoUserRegistrationRequest {
    @JsonProperty("userPoolId")
    private String userPoolId;

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("userAttributes")
    private UserAttributes userAttributes;

    public RegisterUserInput toUserInput() {
        final var input = new RegisterUserInput();
        input.setId(userAttributes.sub);
        input.setName(userAttributes.name);
        input.setEmail(userAttributes.email);
        input.setUsername(userName);
        input.setRegisterType(RegisterUserInput.RegisterType.from(userAttributes.group));
        return input;
    }

    @Getter
    @Setter
    public static class UserAttributes {
        @JsonProperty("sub")
        private String sub;

        @JsonProperty("name")
        private String name;

        @JsonProperty("email")
        private String email;

        @JsonProperty("email_verified")
        private String emailVerified;

        @JsonProperty("group")
        private String group;
    }
}
