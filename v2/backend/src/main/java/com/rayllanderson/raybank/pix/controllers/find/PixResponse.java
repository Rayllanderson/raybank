package com.rayllanderson.raybank.pix.controllers.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PixResponse {
    private String id;
    private BigDecimal amount;
    private String type;
    private Debit debit;
    private Credit credit;
    private String message;
    private LocalDateTime occurredOn;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private QrCodeResponse qrCode;
    private List<PixReturnResponse> returns;

    @Getter
    @Setter
    public static class PixReturnResponse {
        private String id;
        private BigDecimal amount;
        private LocalDateTime occuredOn;
        private String message;
    }

    @Getter
    @Setter
    public static class QrCodeResponse {
        private String id;
        private String code;
        private String description;
    }

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
