package com.rayllanderson.raybank.bankaccount.services.find;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class BankAccountDetailsOutput {
    private final UserOutput user;
    private final AccountOutput account;

    @Getter
    @RequiredArgsConstructor
    protected static class AccountOutput {
        final String id;
        final Integer number;
        final BigDecimal balance;
        final String type;
        final String status;
        final LocalDateTime createAt;
        private final CardOutput card;
    }
    @Getter
    @RequiredArgsConstructor
    protected static class UserOutput {
        final String name;
        final String type;
    }
    @Getter
    @RequiredArgsConstructor
    protected static class CardOutput {
        final String id;
        final String status;
    }
}
