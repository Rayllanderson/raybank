package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.statement.aop.CreateStatement;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.InvoiceTransaction;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class InvoicePaymentService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @CreateStatement
    public Transaction payCurrentInvoice(final PayInvoiceInput input) {
        final BigDecimal amountToPay = input.getAmount();
        final var creditCard = this.creditCardRepository.findById(input.getCardId())
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));

        final InvoiceListHelper invoices = getInvoicesFrom(creditCard);

        final Invoice invoiceToPay = invoices.getCurrentInvoiceToPay();
        final BankAccount bankAccount = creditCard.getBankAccount();

        invoiceToPay.processPayment(amountToPay);
        bankAccount.pay(amountToPay);
        creditCard.receivePayment(amountToPay);

        input.setInvoiceId(invoiceToPay.getId());
        final InvoiceTransaction transaction = InvoiceTransaction.from(input);
        save(creditCard, invoiceToPay, bankAccount, transaction);

        return transaction;
    }

    @Transactional
    public BankStatement payInvoiceById(final PayInvoiceInput input) {
        final var creditCard = this.creditCardRepository.findByBankAccountId(input.getCardId())
                .orElseThrow(() -> new NotFoundException("cartão não encontrado"));

//        final var bankStatement = creditCard.payInvoiceById(input.getInvoiceId(), input.getAmount());

        bankAccountRepository.save(creditCard.getBankAccount());
        creditCardRepository.save(creditCard);

        return null;
    }

    private void save(CreditCard creditCard, Invoice invoiceToPay, BankAccount bankAccount, Transaction transaction) {
        bankAccountRepository.save(bankAccount);
        creditCardRepository.save(creditCard);
        invoiceRepository.save(invoiceToPay);
        transactionRepository.save(transaction);
    }

    private InvoiceListHelper getInvoicesFrom(CreditCard creditCard) {
        final var allInvoicesByCard = invoiceRepository.findAllByCreditCardId(creditCard.getId());
        return new InvoiceListHelper(creditCard.getDayOfDueDate(), allInvoicesByCard);
    }
}
