package com.rayllanderson.raybank.invoice.gateway;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoicePostgresGateway implements InvoiceGateway {

    private final InvoiceRepository invoiceRepository;

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fatura não encontrada"));
    }

    @Override
    public Invoice findCurrentByCardId(String cardId) {
        final var allInvoicesByCard = invoiceRepository.findAllByCreditCardId(cardId);
        final InvoiceListHelper invoices = new InvoiceListHelper(allInvoicesByCard);
        return invoices.getCurrentInvoiceToPay();
    }
}
