package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.models.transaction.CardTransaction;
import com.rayllanderson.raybank.models.transaction.Transaction;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCardStatementService implements CreateStatementService {

    private final BankAccountRepository bankAccountRepository;
    private final BankStatementRepository bankStatementRepository;

    @Override
    @Transactional
    public void process(final Transaction transaction) {
        final var cardTransaction = (CardTransaction) transaction;

        final CreditCard payerCard = cardTransaction.getPayerCard();
        final var payerAccount = bankAccountRepository.findById(payerCard.getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format("Bank Account from payer card id %s was not found", payerCard.getId())));

        if (cardTransaction.isCreditTransaction()) {
            processCreditStatement(cardTransaction, payerAccount);
        } else {
            processDebitStatement(cardTransaction, payerAccount);
        }
        processEstablishmentStatement(cardTransaction);
    }

    private void processEstablishmentStatement(CardTransaction cardTransaction) {
        final var establismentId = cardTransaction.getEstablishment().getId();
        final var establishmentAccount = bankAccountRepository.findByUserId(establismentId)
                .orElseThrow(() -> new NotFoundException(String.format("Bank Account from establishment id %s was not found", establismentId)));

        this.bankStatementRepository.save(BankStatement.receivingCardPayment(
                establishmentAccount,
                cardTransaction.getAmount(),
                cardTransaction.getDescription(),
                cardTransaction.getId(),
                cardTransaction.getInstallments()));
    }

    private void processCreditStatement(final CardTransaction cardTransaction, final BankAccount payerAccount) {
        this.bankStatementRepository.save(BankStatement.createCreditBankStatement(cardTransaction.getAmount(),
                payerAccount,
                cardTransaction.getDescription(),
                cardTransaction.getId(),
                cardTransaction.getInstallments(),
                cardTransaction.getEstablishment()));
    }

    private void processDebitStatement(final CardTransaction cardTransaction, final BankAccount payerAccount) {
        this.bankStatementRepository.save(BankStatement.createDebitCardBankStatement(cardTransaction.getAmount(),
                payerAccount,
                cardTransaction.getDescription(),
                cardTransaction.getId(),
                cardTransaction.getEstablishment()));
    }

    @Override
    public boolean supports(final Transaction transaction) {
        return transaction instanceof CardTransaction;
    }
}
