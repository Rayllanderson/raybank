package com.rayllanderson.raybank.invoice.gateway;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;

import java.util.Collection;
import java.util.List;

public interface InvoiceGateway {

    void save(Invoice invoice);
    void saveAll(Collection<Invoice> invoices);
    Invoice findById(String invoiceId);
    Invoice findCurrentByCardId(String cardId);
    Collection<Invoice> findAllByCardIdAndStatus(String cardId, Collection<InvoiceStatus> status);
    List<Invoice> findAllByCardId(final String cardId);
    List<Invoice> findAllByCardIdWithouInstallments(final String cardId);
    Integer getDayOfDueDateByCardId(final String cardId);
}
