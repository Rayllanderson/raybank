package com.rayllanderson.raybank.card.services.payment;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.Arrays;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_NUMBER_BADLY_FORMATTED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.CARD_SECURITYCODE_BADLY_FORMATTED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVALID_PAYMENT_TYPE;

@Getter
@Setter
public class CardPaymentInput {

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
            throw BadRequestException.withFormatted(CARD_NUMBER_BADLY_FORMATTED, "Numero de Cartão [ %s ] inválido", this.card.number);
        }
    }

    public Integer getCardSecurityCode() {
        try {
            return Integer.valueOf(this.card.securityCode);
        } catch (NumberFormatException e) {
            throw BadRequestException.withFormatted(CARD_SECURITYCODE_BADLY_FORMATTED, "Código de Segurança [ %s ] inválido", this.card.securityCode);
        }
    }

    public YearMonth getCardExpiryDate() {
        return this.card.getExpiryDate();
    }

    public enum PaymentType {
        CREDIT, DEBIT;

        public static PaymentType from(String v) {
            try {
                return PaymentType.valueOf(v.toUpperCase());
            } catch (Exception e) {
                throw BadRequestException.withFormatted(INVALID_PAYMENT_TYPE, "Payment type '%s' is invalid. Available are=%s ", v, Arrays.toString(PaymentType.values()));
            }
        }
    }
}
