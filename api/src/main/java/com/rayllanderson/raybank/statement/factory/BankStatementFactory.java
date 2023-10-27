package com.rayllanderson.raybank.statement.factory;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BankStatementFactory {

    private final List<BankStatementFinder> finders;

    public BankStatement.Credit getCredit(Transaction transaction) {
        return getCreditFinder(transaction).find(transaction.getCredit());
    }

    public BankStatement.Debit getDebit(Transaction transaction) {
        return getDebitFinder(transaction).find(transaction.getDebit());
    }

    private BankStatementFinder getDebitFinder(Transaction transaction) {
        return finders.stream()
                .filter(f -> f.supports(transaction.getDebit().getOrigin()))
                .findFirst()
                .orElseThrow(BankStatementFinderNotFoundException::new);
    }

    private BankStatementFinder getCreditFinder(Transaction transaction) {
        return finders.stream()
                .filter(f -> f.supports(transaction.getCredit().getDestination()))
                .findFirst()
                .orElseThrow(BankStatementFinderNotFoundException::new);
    }

    private static class BankStatementFinderNotFoundException extends RuntimeException {
        public BankStatementFinderNotFoundException() {
            super("No strategies were found for Bank Statement Finder");
        }
    }
}
