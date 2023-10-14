package com.rayllanderson.raybank.card.services.refund;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class DebitEstablishmentRefundProcess implements RefundCardProcess {

    private final DebitAccountFacade debitAccountFacade;

    @Override
    public void refund(Transaction transaction, BigDecimal amount) {
        final var debitEstablishmentInput = DebitAccountFacadeInput.fromRefundCardCredit(transaction, amount);
        debitAccountFacade.process(debitEstablishmentInput);
    }
}
