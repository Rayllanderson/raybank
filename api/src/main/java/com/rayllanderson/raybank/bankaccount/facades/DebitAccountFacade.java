package com.rayllanderson.raybank.bankaccount.facades;

import com.rayllanderson.raybank.bankaccount.services.DebitAccountService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitAccountFacade {

    private final DebitAccountService debitAccountService;

    public Transaction process(final DebitAccountFacadeInput input) {
        return debitAccountService.debit(input.toServiceInput());
    }
}
