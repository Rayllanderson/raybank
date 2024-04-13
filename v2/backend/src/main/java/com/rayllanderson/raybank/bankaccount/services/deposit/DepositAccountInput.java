package com.rayllanderson.raybank.bankaccount.services.deposit;

import java.math.BigDecimal;

public record DepositAccountInput(
        String accountId,
        BigDecimal amount
) {
}
