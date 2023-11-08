package com.rayllanderson.raybank.pix.service.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PixPaymentOutput {
    private String transactionId;
    private String e2eId;
    private Debit debit;
    private Credit credit;
    private BigDecimal amount;
    private LocalDateTime occuredOn;
    private String message;

    @Getter
    @Setter
    protected static class Debit {
        private String name;
    }

    @Getter
    @Setter
    protected static class Credit {
        private String name;
        private String key;
    }
}
