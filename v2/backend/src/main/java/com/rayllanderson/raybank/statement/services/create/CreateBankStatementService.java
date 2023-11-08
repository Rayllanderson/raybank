package com.rayllanderson.raybank.statement.services.create;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.statement.factory.BankStatementFactory;
import com.rayllanderson.raybank.statement.gateway.BankStatementGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateBankStatementService {

    private final BankStatementFactory factory;
    private final BankStatementGateway gateway;
    private final CreateBankStatementMapper mapper;

    @Async
    @Transactional
    @Retryable(maxAttemptsExpression = "${retry.statement.create.maxAttempts}", backoff = @Backoff(maxDelayExpression = "${retry.statement.create.maxDelay}"))
    public void createFrom(Transaction transaction) {
        final BankStatement.Debit debit = factory.getDebit(transaction);
        final BankStatement.Credit credit = factory.getCredit(transaction);

        BankStatement bankStatement;
        if (transaction instanceof CardCreditPaymentTransaction) {
            bankStatement = mapper.from((CardCreditPaymentTransaction) transaction, credit, debit);
        } else {
            bankStatement = mapper.from(transaction, credit, debit);
        }

        gateway.save(bankStatement);
    }

}
