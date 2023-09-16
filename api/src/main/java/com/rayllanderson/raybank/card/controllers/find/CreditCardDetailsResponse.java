package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class CreditCardDetailsResponse {
    private String id;
    private BigDecimal limit;
    private BigDecimal balance;
    private List<InvoiceResponse> invoices;

    public static CreditCardDetailsResponse fromCreditCard(CreditCard c){
        return new ModelMapper().map(c, CreditCardDetailsResponse.class);
    }

    @Getter
    @Setter
    public static class InvoiceResponse {
        private String id;
        private LocalDate dueDate;
        private LocalDate closingDate;
        private BigDecimal total;
        private InvoiceStatus status;
        private List<InstallmentResponse> installments;

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
}
