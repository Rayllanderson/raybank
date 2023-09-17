package com.rayllanderson.raybank.invoice.controllers.find;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.invoice.services.find.FindInvoiceOutput;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    public static FindInvoiceResponse from(FindInvoiceOutput i){
        return new ModelMapper().map(i, FindInvoiceResponse.class);
    }

    public static List<FindInvoiceResponse> map(Collection<FindInvoiceOutput> invoices) {
        return invoices
                .stream()
                .map(FindInvoiceResponse::from)
                .collect(Collectors.toList());
    }

    @Getter
    @Setter
    public static class InstallmentResponse {
        private String id;
        private String description;
        private BigDecimal total;
        private BigDecimal value;
        private LocalDateTime ocurrendOn;
    }
}
