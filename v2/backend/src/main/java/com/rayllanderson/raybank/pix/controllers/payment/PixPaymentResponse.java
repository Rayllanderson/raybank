package com.rayllanderson.raybank.pix.controllers.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PixPaymentResponse {
    private String e2eId;
    private String transactionId;
    private String transactionType;
    private BigDecimal amount;
    private LocalDateTime occuredOn;
    private String message;
    private Debit debit;
    private Credit credit;

    @Getter
    @Setter
    public static class Debit {
        private String name;
    }

    @Getter
    @Setter
    public static class Credit {
        private String name;
        private String key;
    }
}
