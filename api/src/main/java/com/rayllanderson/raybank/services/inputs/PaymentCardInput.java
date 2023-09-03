package com.rayllanderson.raybank.services.inputs;

import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.external.card.payment.PaymentCardRequest;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
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
        }catch (NumberFormatException e){
            throw new BadRequestException(String.format("Código de Segurança [ %s ] inválido", this.card.securityCode));
        }
    }

    public YearMonth getCardExpiryDate() {
        return this.card.getExpiryDate();
    }

    public static PaymentCardInput fromRequest(PaymentCardRequest request){
        return new ModelMapper().map(request, PaymentCardInput.class);
    }
}