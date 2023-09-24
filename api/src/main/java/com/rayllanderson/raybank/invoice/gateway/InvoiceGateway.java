package com.rayllanderson.raybank.invoice.gateway;

import com.rayllanderson.raybank.invoice.models.Invoice;

import java.util.List;

public interface InvoiceGateway {

    void save(Invoice invoice);
    Invoice findById(String invoiceId);
    Invoice findCurrentByCardId(String cardId);
    List<Invoice> findAllByCardId(final String cardId);
    Integer getDayOfDueDateByCardId(final String cardId);
}
