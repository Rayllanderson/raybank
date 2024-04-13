package com.rayllanderson.raybank.pix.service.pixreturn;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PixReturnOutput {
    private String transactionId;
    private String e2eId;
    private String originalE2eId;
    private Debit debit;
    private Credit credit;
    private BigDecimal amount;
    private LocalDateTime occuredOn;
    private String message;

    @Getter
    @Setter
    @AllArgsConstructor
    protected static class Debit {
        private String name;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    protected static class Credit {
        private String name;
    }
}
