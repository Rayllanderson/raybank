package com.rayllanderson.raybank.external.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
public class RegisterExternalTokenRequest {
    @NotBlank
    private final String clientName;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public RegisterExternalTokenRequest(String clientName) {
        this.clientName = clientName;
    }

    public ExternalToken toExternalToken() {
        return new ExternalToken(this.clientName);
    }
}
