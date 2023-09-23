package com.rayllanderson.raybank.invoice.gateway;

import com.rayllanderson.raybank.invoice.models.Invoice;

public interface InvoiceGateway {

    void save(Invoice invoice);
    Invoice findById(String invoiceId);
    Invoice findCurrentByCardId(String cardId);
}
