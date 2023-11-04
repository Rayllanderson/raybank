package com.rayllanderson.raybank.pix.controllers.pixreturn;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class PixReturnResponse {
    private String transactionId;
    private String transactionType;
    private String e2eId;
    private String originalE2eId;
    private Debit debit;
    private Credit credit;
    private BigDecimal amount;
    private LocalDateTime occuredOn;
    private String message;

    @Getter
    @Setter
    public static class Debit {
        private String name;
    }

    @Getter
    @Setter
    public static class Credit {
        private String name;
    }
}
