package com.rayllanderson.raybank.bankaccount.services;

import com.rayllanderson.raybank.shared.dtos.Destination;
import com.rayllanderson.raybank.shared.dtos.Origin;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DebitAccountInput {

    private String accountId;
    private BigDecimal amount;
    private Destination destination;
    private TransactionType transactionType;
}
