package com.rayllanderson.raybank.statement.gateway;

import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import io.github.rayexpresslibraries.ddd.domain.pagination.query.SearchQuery;

import java.util.List;

public interface BankStatementGateway {
    BankStatement findById(final String id);
    List<BankStatement> findAllByAccountId(final String accountId);
    Pagination<BankStatement> findAllByAccountId(final String accountId, SearchQuery query);
    BankStatement save(BankStatement transaction);

    Pagination<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, SearchQuery query, List<TransactionMethod> methods);

    Pagination<BankStatement> findAllByAccountIdAndMethodIn(String accountId, SearchQuery query, List<TransactionMethod> methods);

    Pagination<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, SearchQuery query, Credit.Destination destination, TransactionType type);
}
