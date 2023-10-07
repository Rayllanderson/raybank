package com.rayllanderson.raybank.invoice.gateway;

import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InvoicePostgresGateway implements InvoiceGateway {

    private final InvoiceRepository invoiceRepository;
    private final CardRepository cardRepository;

    @Override
    public Invoice save(final Invoice invoice) {
        return invoiceRepository.saveAndFlush(invoice);
    }

    @Override
    public void saveAll(Collection<Invoice> invoices) {
        this.invoiceRepository.saveAllAndFlush(invoices);
    }

    @Override
    public void flush() {
        this.invoiceRepository.flush();
    }

    @Override
    public Invoice findById(final String id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fatura não encontrada"));
    }

    @Override
    public List<Invoice> findAllByCardId(final String cardId) {
        return invoiceRepository.findAllByCard_Id(cardId);
    }

    @Override
    public Integer getDayOfDueDateByCardId(final String cardId) {
        return cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(String.format("Card %s not found", cardId)))
                .getDayOfDueDate();
    }

    @Override
    public Invoice findCurrentByCardId(final String cardId) {
        final var allInvoicesByCard = this.findAllByCardId(cardId);
        final InvoiceListHelper invoices = new InvoiceListHelper(allInvoicesByCard);
        return invoices.getCurrentInvoiceToPay();
    }

    @Override
    public Collection<Invoice> findAllByCardIdAndStatus(String cardId, Collection<InvoiceStatus> status) {
        return invoiceRepository.findAllByCard_IdAndStatusIn(cardId, new ArrayList<>(status));
    }
}
