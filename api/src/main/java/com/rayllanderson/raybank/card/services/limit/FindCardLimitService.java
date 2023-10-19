package com.rayllanderson.raybank.card.services.limit;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.card.utils.CalculateLimitUtil;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FindCardLimitService {

    private final InvoiceGateway invoiceGateway;

    public BigDecimal findUsedLimit(final String cardId) {
        final List<Invoice> invoices = invoiceGateway.findAllPresentAndFutureByCardId(cardId);
        return CalculateLimitUtil.calculateUsedLimit(invoices);
    }

    public boolean hasAvailableLimit(Card card, BigDecimal value) {
        final var usedLimit = this.findUsedLimit(card.getId());
        final var availableLimit = card.getLimit().subtract(usedLimit);

        return availableLimit.compareTo(value) >= 0;
    }
}
