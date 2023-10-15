package com.rayllanderson.raybank.invoice.services.scheduler;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.shared.event.Event;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InvoiceClosedInput implements Event {

    private final String invoiceId;
    private final String cardId;
    private final BigDecimal total;
    private final LocalDateTime ocurredOn;

    public InvoiceClosedInput(Invoice invoice) {
        this.invoiceId = invoice.getId();
        this.cardId = invoice.getCardId();
        this.total = invoice.getTotal();
        this.ocurredOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }
}
