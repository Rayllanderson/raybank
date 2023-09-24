package com.rayllanderson.raybank.card.facades;

import com.rayllanderson.raybank.card.services.credit.CardCreditService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardCreditFacade {

    private final CardCreditService creditService;

    public Transaction process(final CardCreditFacadeInput cardCreditInput) {
        return creditService.credit(cardCreditInput.toServiceInput());
    }
}
