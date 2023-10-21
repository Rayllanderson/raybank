package com.rayllanderson.raybank.card.services.refund;

import com.rayllanderson.raybank.card.services.credit.CardCreditMapper;
import com.rayllanderson.raybank.card.services.credit.CardCreditService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//@Component
@RequiredArgsConstructor
class CreditCardRefundProcess implements RefundCardProcess {

    private final CardCreditMapper cardCreditMapper;
    private final CardCreditService cardCreditService;

    @Override
    public void refund(Transaction transaction, BigDecimal amount) {
        final var creditCardInput = this.cardCreditMapper.refundInput(transaction, amount);
        cardCreditService.credit(creditCardInput);
    }
}
