package com.rayllanderson.raybank.invoice.controllers.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FindInvoiceResponse {
    private String id;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private BigDecimal total;
    private String status;
    private List<? extends InvoiceTransactionResponse> transactions;

    @Getter
    @Setter
    public abstract static class InvoiceTransactionResponse {
        private String id;
        private BigDecimal value;
        private String description;
        private LocalDate occuredOn;
    }

    @Getter
    @Setter
    public static class InstallmentTransactionResponse extends InvoiceTransactionResponse {
        private String planId;
        private String status;
    }

    @Getter
    @Setter
    public static class CreditTransactionResponse extends InvoiceTransactionResponse {
        private String type;
    }
}
