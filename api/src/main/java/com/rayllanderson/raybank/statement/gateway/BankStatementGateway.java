package com.rayllanderson.raybank.statement.gateway;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;

import java.util.List;
import java.util.Optional;

public interface BankStatementGateway {
    BankStatement findById(final String id);
    List<BankStatement> findAllByAccountId(final String accountId);
    BankStatement save(BankStatement transaction);
    List<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<TransactionMethod> methods);
    List<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<TransactionMethod> methods);
    List<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, Credit.Destination destination, TransactionType type);
}
