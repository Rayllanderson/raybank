package com.rayllanderson.raybank.bankaccount.services.credit;

import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreditAccountInput {

    private String accountId;
    private BigDecimal amount;
    private Origin origin;
    private TransactionType transactionType;
    private TransactionMethod transactionMethod;
}
