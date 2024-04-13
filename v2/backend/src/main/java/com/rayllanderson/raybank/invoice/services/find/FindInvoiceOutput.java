package com.rayllanderson.raybank.invoice.services.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindInvoiceOutput {
    private String id;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private BigDecimal total;
    private String status;
    private List<InstallmentOutput> installments;
    private List<CreditOutput> credits;

    @Getter
    @Setter
    public static class InstallmentOutput {
        private String id;
        private BigDecimal value;
        private String description;
        private String planId;
        private String status;
        private LocalDateTime occuredOn;
    }

    @Getter
    @Setter
    public static class CreditOutput {
        private String id;
        private BigDecimal amount;
        private String type;
        private String description;
        private LocalDateTime occuredOn;
    }
}
