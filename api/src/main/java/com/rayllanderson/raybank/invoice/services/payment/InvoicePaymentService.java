package com.rayllanderson.raybank.invoice.services.payment;

import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.debit.DebitAccountFacadeInput;
import com.rayllanderson.raybank.invoice.events.InvoicePaidEvent;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCreditType;
import com.rayllanderson.raybank.invoice.models.inputs.ProcessInvoiceCredit;
import com.rayllanderson.raybank.shared.event.IntegrationEventPublisher;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.rayllanderson.raybank.invoice.constants.InvoiceCreditDescriptionConstant.INVOICE_PAYMENT_DESCRIPTION;

@Service
@RequiredArgsConstructor
public class InvoicePaymentService {

    private final InvoiceGateway invoiceGateway;
    private final DebitAccountFacade debitAccountFacade;
    private final IntegrationEventPublisher eventPublisher;

    @Transactional
    public Transaction payCurrent(final InvoicePaymentInput input) {
        final var currentInvoice = invoiceGateway.findCurrentByCardId(input.getCardId());

        input.setInvoiceId(currentInvoice.getId());
        return processPayment(input, currentInvoice);
    }

    @Transactional
    public Transaction payById(final InvoicePaymentInput input) {
        final Invoice invoiceToPay = invoiceGateway.findById(input.getInvoiceId());

        return processPayment(input, invoiceToPay);
    }

    private Transaction processPayment(final InvoicePaymentInput input, final Invoice invoiceToPay) {
        final var debit = DebitAccountFacadeInput.from(input);
        final Transaction debitTransaction = debitAccountFacade.process(debit);

        final var creditInput = new ProcessInvoiceCredit(input.getAmount(), InvoiceCreditType.INVOICE_PAYMENT, INVOICE_PAYMENT_DESCRIPTION, debitTransaction.getId(), LocalDateTime.now());
        invoiceToPay.processCredit(creditInput);

        eventPublisher.publish(new InvoicePaidEvent(invoiceToPay, debitTransaction));

        return debitTransaction;
    }
}
