package com.rayllanderson.raybank.invoice.controllers.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    public abstract static class InvoiceTransactionResponse implements Comparable<InvoiceTransactionResponse> {
        private String id;
        private BigDecimal value;
        private String description;
        private LocalDateTime occuredOn;
        private String type;

        @Override
        public int compareTo(InvoiceTransactionResponse o) {
            if (this.occuredOn.isBefore(o.occuredOn)) return 1;
            if (this.occuredOn.isAfter(o.occuredOn)) return -1;
            else return 0;
        }
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

    }
}
