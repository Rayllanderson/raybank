package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.exceptions.NotFoundException;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.card.transactions.payment.CardPaymentTransaction;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCardPaymentStatementService implements CreateStatementService {

    private final BankAccountRepository bankAccountRepository;
    private final BankStatementRepository bankStatementRepository;

    @Override
    @Transactional
    public void process(final Transaction transaction) {
        final var cardTransaction = (CardPaymentTransaction) transaction;

        final Card payerCard = null;
        final var payerAccount = bankAccountRepository.findById(payerCard.getAccountId())
                .orElseThrow(() -> new NotFoundException(String.format("Bank Account from payer card id %s was not found", payerCard.getId())));

        if (cardTransaction.isCreditTransaction()) {
            processCreditStatement(cardTransaction, payerAccount);
        } else {
            processDebitStatement(cardTransaction, payerAccount);
        }
        processEstablishmentStatement(cardTransaction);
    }

    private void processEstablishmentStatement(CardPaymentTransaction cardPaymentTransaction) {
//        final var establismentId = cardPaymentTransaction.getEstablishmentId();
        final String establismentId = null;
        final var establishmentAccount = bankAccountRepository.findByUserId(establismentId)
                .orElseThrow(() -> new NotFoundException(String.format("Bank Account from establishment id %s was not found", establismentId)));

        this.bankStatementRepository.save(BankStatement.receivingCardPayment(
                establishmentAccount,
                cardPaymentTransaction.getAmount(),
                cardPaymentTransaction.getDescription(),
                cardPaymentTransaction.getId(),
                0));
    }

    private void processCreditStatement(final CardPaymentTransaction cardPaymentTransaction, final BankAccount payerAccount) {
        this.bankStatementRepository.save(BankStatement.createCreditBankStatement(cardPaymentTransaction.getAmount(),
                payerAccount,
                cardPaymentTransaction.getDescription(),
                cardPaymentTransaction.getId(),
                0,
                null)); //todo
    }

    private void processDebitStatement(final CardPaymentTransaction cardPaymentTransaction, final BankAccount payerAccount) {
        this.bankStatementRepository.save(BankStatement.createDebitCardBankStatement(cardPaymentTransaction.getAmount(),
                payerAccount,
                cardPaymentTransaction.getDescription(),
                cardPaymentTransaction.getId(),
                null)); //todo
    }

    @Override
    public boolean supports(final Transaction transaction) {
        return transaction instanceof CardPaymentTransaction;
    }
}
