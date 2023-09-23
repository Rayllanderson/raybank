package com.rayllanderson.raybank.invoice.services.payment;

import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacadeInput;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.statement.aop.CreateStatement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.invoice.InvoicePaymentTransaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InvoicePaymentService {

    private final InvoiceGateway invoiceGateway;
    private final DebitAccountFacade debitAccountFacade;
    private final TransactionRepository transactionRepository;

    @Transactional
    @CreateStatement
    public Transaction payCurrent(final InvoicePaymentInput input) {
        final var currentInvoice = invoiceGateway.findCurrentByCardId(input.getCardId());

        input.setInvoiceId(currentInvoice.getId());
        return processPayment(input, currentInvoice);
    }

    @Transactional
    @CreateStatement
    public Transaction payById(final InvoicePaymentInput input) {
        final Invoice invoiceToPay = invoiceGateway.findById(input.getInvoiceId());

        return processPayment(input, invoiceToPay);
    }

    private Transaction processPayment(final InvoicePaymentInput input, final Invoice invoiceToPay) {
        final var debit = DebitAccountFacadeInput.from(input);
        debitAccountFacade.process(debit);

        invoiceToPay.processPayment(input.getAmount());

        return transactionRepository.save(InvoicePaymentTransaction.from(input));
    }
}
