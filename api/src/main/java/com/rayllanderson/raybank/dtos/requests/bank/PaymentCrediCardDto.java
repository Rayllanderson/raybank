package com.rayllanderson.raybank.dtos.requests.bank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.YearMonth;

@Getter
@Setter
@Builder
public class PaymentCrediCardDto {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull
    private PaymentTypeDto paymentType;

    @Valid
    @NotNull
    private Card card;

    @Getter
    @Setter
    @Builder
    public static class Card {
        @NotNull
        @Size(min = 16, max = 16)
        private String number;

        @NotNull
        @Size(min = 3, max = 3)
        private String securityCode;

        @NotNull
        @FutureOrPresent
        private YearMonth expiryDate;
    }

    @JsonIgnore
    public boolean isCreditPayment() {
        return PaymentTypeDto.CREDIT.equals(this.getPaymentType());
    }

    @JsonIgnore
    public Long getCardNumber() {
        try {
            return Long.valueOf(this.card.number);
        } catch (NumberFormatException e) {
            throw new BadRequestException(String.format("Numero de Cartão [ %s ] inválido", this.card.number));
        }
    }

    @JsonIgnore
    public Integer getCardSecurityCode() {
        try {
            return Integer.valueOf(this.card.securityCode);
        }catch (NumberFormatException e){
            throw new BadRequestException(String.format("Código de Segurança [ %s ] inválido", this.card.securityCode));
        }
    }

    @JsonIgnore
    public YearMonth getCardExpiryDate() {
        return this.card.getExpiryDate();
    }

}
