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

    public List<FindInvoiceListOutput> findAllByCardId(final String cardId) {
        final var invoices = invoiceGateway.findAllByCardId(cardId);

        Collections.sort(invoices);

        return mapper.from(invoices);
    }

    public List<FindInvoiceListOutput> findAllByAccountId(final String accountId) {
        final var invoices = invoiceGateway.findAllByAccountId(accountId);

        Collections.sort(invoices);

        return mapper.from(invoices);
    }

    public FindInvoiceOutput findCurrentAccountId(final String accountId) {
        final var invoice = invoiceGateway.findCurrentByAccountId(accountId);
        return mapper.from(invoice);
    }

    public FindInvoiceOutput findById(final String id) {
        final var invoice = invoiceGateway.findById(id);

        return mapper.from(invoice);
    }

}
