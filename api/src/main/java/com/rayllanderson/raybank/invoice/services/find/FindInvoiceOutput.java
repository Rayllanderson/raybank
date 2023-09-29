package com.rayllanderson.raybank.invoice.services.find;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @Getter
    @Setter
    public static class InstallmentOutput {
        private String id;
        private BigDecimal value;
        private String description;
        private String planId;
        private String status;
        private LocalDate dueDate;
    }
}
