package com.rayllanderson.raybank.invoice.services.refund;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RefundInvoiceService {
    private final InvoiceGateway invoiceGateway;
    private final TransactionGateway transactionGateway;

    @Transactional
    public Transaction credit(final RefundInvoiceInput input) {
        final Invoice invoice = invoiceGateway.findCurrentByCardId(input.getCardId());

        final var creditInput = new ProcessInvoiceCredit(input.getAmountToBeCredited(),
                InvoiceCreditType.REFUND,
                input.getDescription(),
                input.getTransactionId(),
                LocalDateTime.now());

        invoice.processCredit(creditInput);

        final Transaction debitTransaction = transactionGateway.findById(input.getTransactionId());
        final Transaction refundInvoiceTransaction = Transaction.refundInvoice(input.getAmountToBeCredited(), invoice.getCard().getAccountId(), debitTransaction);
        transactionGateway.save(refundInvoiceTransaction);

        return refundInvoiceTransaction;
    }
}
