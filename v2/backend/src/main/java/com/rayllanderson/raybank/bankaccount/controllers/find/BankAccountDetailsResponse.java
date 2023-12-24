package com.rayllanderson.raybank.bankaccount.controllers.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class BankAccountDetailsResponse {
    private final UserResponse user;
    private final AccountResponse account;
    private final CardResponse card;

    @Getter
    @RequiredArgsConstructor
    public static class AccountResponse {
        final String id;
        final Integer number;
        final BigDecimal balance;
        final String type;
        final String status;
        final LocalDateTime createAt;
    }
    @Getter
    @RequiredArgsConstructor
    public static class UserResponse {
        final String name;
        final String type;
    }
    @Getter
    @RequiredArgsConstructor
    public static class CardResponse {
        final String id;
        final String status;
    }
}
