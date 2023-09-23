package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacadeInput;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.invoice.services.find.FindInvoiceService;
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

    private final FindInvoiceService findInvoiceService;
    private final InvoiceRepository invoiceRepository;
    private final DebitAccountFacade debitAccountFacade;
    private final TransactionRepository transactionRepository;

    @Transactional
    @CreateStatement
    public Transaction payCurrent(final PayInvoiceInput input) {
        final var currentInvoice = findInvoiceService.findCurrentByCardId(input.getCardId())
                .orElseThrow(() -> new NotFoundException("Fatura atual não existe"));

        input.setInvoiceId(currentInvoice.getId());
        return processPayment(input, currentInvoice);
    }

    @Transactional
    @CreateStatement
    public Transaction payById(final PayInvoiceInput input) {
        final Invoice invoiceToPay = invoiceRepository.findById(input.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("Fatura não encontrada"));

        return processPayment(input, invoiceToPay);
    }

    private Transaction processPayment(PayInvoiceInput input, Invoice invoiceToPay) {
        final var debit = DebitAccountFacadeInput.from(input);
        debitAccountFacade.process(debit);

        invoiceToPay.processPayment(input.getAmount());

        return transactionRepository.save(InvoicePaymentTransaction.from(input));
    }
}
