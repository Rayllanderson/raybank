package com.rayllanderson.raybank.invoice.services;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.card.repository.CreditCardRepository;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.statement.aop.CreateStatement;
import com.rayllanderson.raybank.transaction.models.InvoiceTransaction;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentInvoiceService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final InvoiceRepository invoiceRepository;
    private final TransactionRepository transactionRepository;

    //pagar invoice tem que gerar 3 transacoes? bah...

    // uma geral (que une a transação) // uma operacao?
    // uma para debitar
    // uma para creditar no cartão

    @Transactional
    @CreateStatement
    public Transaction payCurrentInvoice(final PayInvoiceInput input) {
        final CreditCard creditCard = getCardById(input.getCardId());

        final InvoiceListHelper invoices = getInvoicesFrom(creditCard);
        final Invoice invoiceToPay = invoices.getCurrentInvoiceToPay();

        return processPayment(input, creditCard, invoiceToPay);
    }

    @Transactional
    @CreateStatement
    public Transaction payInvoiceById(final PayInvoiceInput input) {
        final CreditCard creditCard = getCardById(input.getCardId());

        final Invoice invoiceToPay = invoiceRepository.findById(input.getInvoiceId())
                .orElseThrow(() -> new NotFoundException("Fatura não encontrada"));

        return processPayment(input, creditCard, invoiceToPay);
    }

    private Transaction processPayment(PayInvoiceInput input, CreditCard creditCard, Invoice invoiceToPay) {
        final BigDecimal amountToPay = input.getAmount();
        final BankAccount bankAccount = creditCard.getBankAccount();

        invoiceToPay.processPayment(amountToPay);
        bankAccount.pay(amountToPay);
        creditCard.receivePayment(amountToPay);

        input.setInvoiceId(invoiceToPay.getId());
        final InvoiceTransaction transaction = InvoiceTransaction.from(input);
        save(creditCard, invoiceToPay, bankAccount, transaction);

        return transaction;
    }

    private CreditCard getCardById(String cardId) {
        return this.creditCardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException("Cartão não encontrado"));
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
