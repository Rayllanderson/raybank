package com.rayllanderson.raybank.invoice.services.find;

import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

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

    public static FindInvoiceOutput withtInstallments(final Invoice i){
        return new ModelMapper().map(i, FindInvoiceOutput.class);
    }

    public static FindInvoiceOutput withoutInstallments(final Invoice i){
        return new FindInvoiceOutput(i.getId(),
                i.getDueDate(),
                i.getClosingDate(),
                i.getTotal(),
                i.getStatus().name(),
                null);
    }

    @Getter
    @Setter
    public static class InstallmentOutput {
        private String id;
        private String description;
        private BigDecimal total;
        private BigDecimal value;
        private LocalDateTime ocurrendOn;
    }
}
