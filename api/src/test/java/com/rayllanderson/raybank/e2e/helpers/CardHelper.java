package com.rayllanderson.raybank.e2e.helpers;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.card.services.payment.CardPaymentService;
import com.rayllanderson.raybank.e2e.builders.CardPaymentBuilder;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CardHelper {
    @Autowired
    private CardPaymentService cardPaymentService;

    public Transaction doPayment(BigDecimal amount,
                                 CardPaymentInput.PaymentType paymentType,
                                 Integer installments,
                                 String description,
                                 String establishmentId,
                                 Card card) {
        CardPaymentInput paymentInput = CardPaymentBuilder.buildInput(amount, paymentType, installments, description, establishmentId, card);
        return cardPaymentService.pay(paymentInput);
    }

    public Transaction doCreditPayment(BigDecimal amount,
                                 Integer installments,
                                 String description,
                                 String establishmentId,
                                 Card card) {
        return doPayment(amount, CardPaymentInput.PaymentType.CREDIT, installments, description, establishmentId, card);
    }

    public Transaction doCreditPayment(BigDecimal amount,
                                       String establishmentId,
                                       Card card) {
        return doPayment(amount, CardPaymentInput.PaymentType.CREDIT, 1, "Alibaba", establishmentId, card);
    }
}
