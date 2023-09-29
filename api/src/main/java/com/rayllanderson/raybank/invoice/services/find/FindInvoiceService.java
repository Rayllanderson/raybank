package com.rayllanderson.raybank.invoice.services.find;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindInvoiceService {

    private final InvoiceGateway invoiceGateway;
    private final FindInvoiceMapper mapper;

    public List<FindInvoiceOutput> findAllByCardId(final String cardId) {
        final var invoices = invoiceGateway.findAllByCardIdWithouInstallments(cardId);

        Collections.sort(invoices);

        return mapper.from(invoices);
    }

    public FindInvoiceOutput findById(final String id) {
        final var invoice = invoiceGateway.findById(id);

        return mapper.from(invoice);
    }

}
