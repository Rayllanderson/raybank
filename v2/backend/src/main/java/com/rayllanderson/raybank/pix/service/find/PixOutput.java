package com.rayllanderson.raybank.pix.service.find;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PixOutput {
    private String id;
    private BigDecimal amount;
    private String type;
    private Debit debit;
    private Credit credit;
    private String message;
    private LocalDateTime occuredOn;
    private QrCodeOutput qrCode;
    private List<PixReturnOutput> returns;

    @Getter
    @Setter
    protected static class PixReturnOutput {
        private String id;
        private BigDecimal amount;
        private LocalDateTime occuredOn;
        private String message;
    }

    @Getter
    @Setter
    protected static class QrCodeOutput {
        private String id;
        private String code;
        private String description;
    }

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
