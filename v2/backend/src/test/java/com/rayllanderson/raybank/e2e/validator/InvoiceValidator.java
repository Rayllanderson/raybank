package com.rayllanderson.raybank.e2e.validator;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.assertj.core.api.AbstractComparableAssert;
import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public interface InvoiceValidator {

    default ListAssert<Invoice> assertThatInvoicesFromCard(String cardId) {
        final var allByAccountId = getInvoiceRepository().findAllByCard_Id(cardId);
        return assertThat(allByAccountId);
    }

    default AbstractComparableAssert<?, Invoice> assertThatCurrentInvoiceFromCard(String cardId) {
        final var invoice = firstBy(cardId);
        return assertThat(invoice);
    }

    default AbstractBigDecimalAssert<?> assertThatCurrentInvoiceTotalFromCard(String cardId) {
        final var invoice = firstBy(cardId);
        return assertThat(invoice.getTotal());
    }

    private Invoice firstBy(String cardId) {
        return getInvoiceRepository().findAllByCard_Id(cardId).stream().findFirst().get();
    }

    InvoiceRepository getInvoiceRepository();
}
