package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.statement.models.CardCreditStatement;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.card.transactions.credit.CardCreditTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateCardCreditStatementService implements CreateStatementService {

    private final BankStatementRepository bankStatementRepository;

    @Override
    @Transactional
    public void process(final Transaction transaction) {
        final CardCreditTransaction creditTransaction = (CardCreditTransaction) transaction;

        final var cardCreditStatement = CardCreditStatement.fromCardCreditTransaction(creditTransaction);
        bankStatementRepository.save(cardCreditStatement);
    }

    @Override
    public boolean supports(final Transaction transaction) {
        return transaction instanceof CardCreditTransaction;
    }
}
