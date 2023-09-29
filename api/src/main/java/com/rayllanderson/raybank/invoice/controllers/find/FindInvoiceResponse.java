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
    private List<InstallmentResponse> installments;

    @Getter
    @Setter
    public static class InstallmentResponse {
        private String id;
        private BigDecimal value;
        private String description;
        private String planId;
        private String status;
        private LocalDate dueDate;
    }
}
