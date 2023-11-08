package com.rayllanderson.raybank.external.token;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class RegisterExternalTokenResponse {
    private final String token;
    private final LocalDate createdIn;

    public RegisterExternalTokenResponse(String token, LocalDate createdIn) {
        this.token = token;
        this.createdIn = createdIn;
    }
}
