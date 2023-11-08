package com.rayllanderson.raybank.bankaccount.facades.credit;

import com.rayllanderson.raybank.bankaccount.services.credit.CreditAccountService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreditAccountFacade {

    private final CreditAccountService creditAccountService;

    public Transaction process(final CreditAccountFacadeInput cardCreditInput) {
        return creditAccountService.credit(cardCreditInput.toServiceInput());
    }
}
