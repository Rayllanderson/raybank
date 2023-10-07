package com.rayllanderson.raybank.boleto.services.find;

import com.rayllanderson.raybank.boleto.BoletoConstant;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class BoletoDetailsOutput {

    private BoletoData boleto;
    private Beneficiary beneficiary;
    private InstitutionIssuing institutionIssuing;

    @Getter
    @Setter
    static class BoletoData {
        private String barCode;
        private BigDecimal value;
        private String status;
        private LocalDate createdAt;
        private LocalDate expirationDate;
    }

    @Getter
    @Setter
    static class Holder {
        private String id;
        private Account account;
    }

    @Getter
    @Setter
    static class Account {
        private String id;
        private String name;
        private String number;
    }

    @Getter
    @Setter
    static class Beneficiary {
        private String type;
        private Account account;
        private Invoice invoice;
    }

    @Getter
    @Setter
    static class Invoice {
        private String id;
    }

    @Getter
    static class InstitutionIssuing {
        private final Integer code = BoletoConstant.PAYMENT_INSTITUTION_CODE;
        private final String name = BoletoConstant.PAYMENT_INSTITUTION_NAME;
    }
}
