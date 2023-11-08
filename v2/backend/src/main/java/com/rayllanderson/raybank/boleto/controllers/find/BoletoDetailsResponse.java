package com.rayllanderson.raybank.boleto.controllers.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BoletoDetailsResponse {
    private BoletoResponse boleto;
    private BoletoBeneficiaryResponse beneficiary;
    private BoletoInstitutionIssuingResponse institutionIssuing;

    @Getter
    @Setter
    public static class BoletoResponse {
        private String barCode;
        private BigDecimal value;
        private String status;
        private LocalDate createdAt;
        private LocalDate expirationDate;
    }

    @Getter
    @Setter
    public static class BoletoHolderResponse {
        private String id;
        private BoletoAccountResponse account;
    }

    @Getter
    @Setter
    public static class BoletoAccountResponse {
        private String id;
        private String name;
        private String number;
    }

    @Getter
    @Setter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BoletoBeneficiaryResponse {
        private String type;
        private BoletoAccountResponse account;
        private BoletoInvoiceResponse invoice;
    }

    @Getter
    @Setter
    public static class BoletoInvoiceResponse {
        private String id;
    }

    @Getter
    @Setter
    public static class BoletoInstitutionIssuingResponse {
        private Integer code;
        private String name;
    }
}
