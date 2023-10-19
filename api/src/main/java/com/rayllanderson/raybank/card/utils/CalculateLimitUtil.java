package com.rayllanderson.raybank.card.utils;

import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.utils.MathUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CalculateLimitUtil {

    public static BigDecimal calculateUsedLimit(Collection<Invoice> invoices) {
        return MathUtils.sum(invoices.stream().filter(i -> !i.hasRemainingBalance()).map(Invoice::getTotal));
    }
}
