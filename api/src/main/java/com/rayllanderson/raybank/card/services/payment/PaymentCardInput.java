package com.rayllanderson.raybank.card.services.payment;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.card.controllers.external.PaymentCardRequest;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

@Getter
@Setter
public class PaymentCardInput {

    private BigDecimal amount;
    private PaymentType paymentType;
    private Integer installments;
    private LocalDateTime ocurredOn;
    private String description;
    private String establishmentId;
    private Card card;

    @Getter
    @Setter
    public static class Card {
        private String number;
        private String securityCode;
        private YearMonth expiryDate;
    }

    public boolean isCreditPayment() {
        return PaymentType.CREDIT.equals(this.getPaymentType());
    }

    public Long getCardNumber() {
        try {
            return Long.valueOf(this.card.number);
        } catch (NumberFormatException e) {
            throw new BadRequestException(String.format("Numero de Cartão [ %s ] inválido", this.card.number));
        }
    }

    public Integer getCardSecurityCode() {
        try {
            return Integer.valueOf(this.card.securityCode);
        } catch (NumberFormatException e) {
            throw new BadRequestException(String.format("Código de Segurança [ %s ] inválido", this.card.securityCode));
        }
    }

    public YearMonth getCardExpiryDate() {
        return this.card.getExpiryDate();
    }

    public static PaymentCardInput fromRequest(PaymentCardRequest request) {
        return new ModelMapper().map(request, PaymentCardInput.class);
    }

    public enum PaymentType {
        CREDIT, DEBIT
    }
}
