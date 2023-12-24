package com.rayllanderson.raybank.e2e.validator;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repositories.BankStatementRepository;
import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public interface StatementValidator {

    default ListAssert<BankStatement> assertThatStatementsFromAccount(String accountId) {
        final var allByAccountId = getBankSatatementRepository().findAllByAccountId(accountId);
        return assertThat(allByAccountId);
    }

    BankStatementRepository getBankSatatementRepository();
}
