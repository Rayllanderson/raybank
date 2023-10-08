package com.rayllanderson.raybank.invoice.events;

import com.rayllanderson.raybank.shared.event.Event;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InvoiceClosedEvent implements Event {

    private final String invoiceId;
    private final String cardId;
    private final BigDecimal total;
    private final LocalDateTime ocurredOn;

    public InvoiceClosedEvent(Invoice invoice) {
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
