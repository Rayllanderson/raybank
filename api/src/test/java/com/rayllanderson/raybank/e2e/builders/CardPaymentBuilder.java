package com.rayllanderson.raybank.e2e.builders;

import com.rayllanderson.raybank.card.controllers.payment.CardPaymentRequest;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CardPaymentBuilder {

    public static CardPaymentRequest cardPaymentRequest(double amount, String paymentType, Integer installments, String description, CardPaymentRequest.Card card) {
        return cardPaymentRequest(BigDecimal.valueOf(amount), paymentType, installments, description, card);
    }

    public static CardPaymentRequest cardPaymentRequest(BigDecimal amount, String paymentType, Integer installments, String description, CardPaymentRequest.Card card) {
        final var request = new CardPaymentRequest();

        request.setAmount(amount);
        request.setPaymentType(paymentType);
        request.setInstallments(installments);
        request.setOcurredOn(LocalDateTime.now());
        request.setDescription(description);
        request.setCard(card);

        return request;
    }

    public static CardPaymentInput buildInput(
            BigDecimal amount,
            CardPaymentInput.PaymentType paymentType,
            Integer installments,
            String description,
            String establishmentId,
            Card card) {

        CardPaymentInput input = new CardPaymentInput();
        input.setAmount(amount);
        input.setPaymentType(paymentType);
        input.setInstallments(installments);
        input.setOcurredOn(LocalDateTime.now());
        input.setDescription(description);
        input.setEstablishmentId(establishmentId);

        CardPaymentInput.Card cardInput = new CardPaymentInput.Card();
        cardInput.setNumber(card.getNumber().toString());
        cardInput.setSecurityCode(card.getSecurityCode().toString());
        cardInput.setExpiryDate(card.getExpiryDate());

        input.setCard(cardInput);

        return input;
    }

    public static CardPaymentRequest.Card cardRequestFrom(Card card) {
        final var cardRequest = new CardPaymentRequest.Card();
        cardRequest.setNumber(String.valueOf(card.getNumber()));
        cardRequest.setSecurityCode(card.getSecurityCode().toString());
        cardRequest.setExpiryDate(card.getExpiryDate());
        return cardRequest;
    }
}
