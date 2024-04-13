package com.rayllanderson.raybank.invoice.services.credit;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.invoice.events.InvoicePaidEvent;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import com.rayllanderson.raybank.invoice.services.credit.validator.InvoiceCreditValidator;
import com.rayllanderson.raybank.shared.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.rayllanderson.raybank.shared.constants.DescriptionConstant.PAYMENT_DESCRIPTION;

@Service
@RequiredArgsConstructor
public class InvoiceCreditService {

    private final InvoiceGateway invoiceGateway;
    private final TransactionGateway transactionGateway;
    private final DebitAccountFacade debitAccountFacade;
    private final IntegrationEventPublisher eventPublisher;
    private final InvoiceCreditValidator invoiceCreditValidator;

    @Transactional
    public Transaction creditCurrent(final InvoiceCreditInput input) {
        final var currentInvoice = invoiceGateway.findCurrentByAccountId(input.getAccountId());
        input.setInvoiceId(currentInvoice.getId());

        return getTransaction(input, currentInvoice);
    }

    @Transactional
    public Transaction creditById(final InvoiceCreditInput input) {
        final Invoice invoiceToPay = invoiceGateway.findById(input.getInvoiceId());

        return getTransaction(input, invoiceToPay);
    }

    @Transactional
    public void credit(final InvoiceReceiveCreditInput creditInput) {
        final var debitTransaction = transactionGateway.findById(creditInput.getDebitTransactionId());

        final Invoice invoiceToPay = invoiceGateway.findById(creditInput.getInvoiceId());

        invoiceCreditValidator.validaIfcanReceiveCredit(invoiceToPay, creditInput.getAmount());

        processCredit(creditInput.getAmount(), invoiceToPay, debitTransaction);
    }

    private Transaction getTransaction(InvoiceCreditInput input, Invoice invoice) {
        invoiceCreditValidator.validaIfcanReceiveCredit(invoice, input.getAmount());

        final var debit = DebitAccountFacadeInput.from(input);
        final Transaction debitTransaction = debitAccountFacade.process(debit);

        return processCredit(input.getAmount(), invoice, debitTransaction);
    }

    private Transaction processCredit(BigDecimal amount, final Invoice invoiceToPay, Transaction debitTransaction) {
        final var creditInput = new ProcessInvoiceCredit(amount, InvoiceCreditType.INVOICE_PAYMENT, PAYMENT_DESCRIPTION, debitTransaction.getId(), LocalDateTime.now());
        invoiceToPay.processCredit(creditInput);

        eventPublisher.publish(new InvoicePaidEvent(invoiceToPay, debitTransaction));

        final Transaction creditInvoiceTransaction = Transaction.creditInvoice(amount, invoiceToPay.getCard().getAccountId(), debitTransaction);
        transactionGateway.save(creditInvoiceTransaction);

        return debitTransaction;
    }
}
