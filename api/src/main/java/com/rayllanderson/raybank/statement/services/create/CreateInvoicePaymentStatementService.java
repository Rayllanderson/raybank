package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.repository.CardRepository;
import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.invoice.repository.InvoiceRepository;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateInvoicePaymentStatementService implements CreateStatementService {

    private final CardRepository cardRepository;
    private final InvoiceRepository invoiceRepository;
    private final BankStatementRepository bankStatementRepository;

    @Override
    @Transactional
    public void process(final Transaction transaction) {
        final String invoiceId = null;
        final var invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NotFoundException(String.format("Invoice %s not found", invoiceId)));

        final String cardId = invoice.getCardId();
        final var card = cardRepository.findById(cardId)
                .orElseThrow(() -> new NotFoundException(String.format("card %s was not found", cardId)));

        final var invoiceBankStatement = BankStatement.createInvoicePaymentBankStatement(transaction.getAmount(), BankAccount.withId(card.getAccountId()), transaction.getId());
        bankStatementRepository.save(invoiceBankStatement);
    }

    @Override
    public boolean supports(final Transaction transaction) {
        return transaction instanceof Transaction;
    }
}
