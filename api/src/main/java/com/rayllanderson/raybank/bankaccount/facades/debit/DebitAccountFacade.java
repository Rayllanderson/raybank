package com.rayllanderson.raybank.bankaccount.facades.debit;

import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountMapper;
import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebitAccountFacade {

    private final DebitAccountService debitAccountService;
    private final DebitAccountMapper debitAccountMapper;

    public Transaction process(final DebitAccountFacadeInput input) {
        return debitAccountService.debit(debitAccountMapper.from(input));
    }
}
