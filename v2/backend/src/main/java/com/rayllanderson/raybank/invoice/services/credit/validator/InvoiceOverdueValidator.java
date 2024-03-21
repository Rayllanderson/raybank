package com.rayllanderson.raybank.invoice.services.credit.validator;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_PARTIAL_PAYMENT_NOT_AVAILABLE;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isBeforeOrEquals;

@Component
class InvoiceOverdueValidator implements InvoiceValidator {
    @Override
    public void validate(Invoice invoice, BigDecimal amountToBeCredited) {
        if ((isPaymentDate(invoice) || invoice.isOverdue()) && isPartialPayment(invoice, amountToBeCredited)) {
            throw UnprocessableEntityException.with(INVOICE_PARTIAL_PAYMENT_NOT_AVAILABLE, "Não é possível receber pagamento parcial para fatura fechada ou vencida.");
        }
    }

    protected boolean isPartialPayment(Invoice invoice, BigDecimal amount) {
        return !(amount.compareTo(invoice.getTotal()) == 0 || amount.compareTo(invoice.getTotal()) > 0);
    }

    protected boolean isPaymentDate(Invoice invoice) {
        final var now = LocalDate.now();
        return isAfterOrEquals(now, invoice.getClosingDate()) && isBeforeOrEquals(now, invoice.getDueDate());
    }
}
