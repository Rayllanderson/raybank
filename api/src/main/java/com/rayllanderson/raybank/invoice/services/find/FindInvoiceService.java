package com.rayllanderson.raybank.invoice.services.find;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindInvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<FindInvoiceOutput> findAllByCardId(final String cardId) {
        final var invoices = invoiceRepository.findAllByCreditCardId(cardId);

        Collections.sort(invoices);

        return invoices.stream().map(FindInvoiceOutput::withoutInstallments)
                .collect(Collectors.toList());
    }

    public FindInvoiceOutput findById(final String id) {
        final var invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fatura não encontrada"));

        return FindInvoiceOutput.withtInstallments(invoice);
    }

}
