package com.rayllanderson.raybank.statement.services.find;

import com.rayllanderson.raybank.statement.gateway.BankStatementGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class BankStatementFinderService {

    private final BankStatementMapper mapper;
    private final BankStatementGateway bankStatementGateway;
    public static final List<TransactionMethod> CREDIT_CARD_TRANSACTIONS = List.of(TransactionMethod.CREDIT_CARD);

    @Transactional(readOnly = true)
    public List<BankStatementOutput> findAllByAccountId(final String accountId) {
        var statements = bankStatementGateway.findAllByAccountId(accountId);
        return mapper.from(statements);
    }

    @Transactional(readOnly = true)
    public List<BankStatementOutput> findAllAccountStatementsByAccountId(final String accountId) {
        var statements = bankStatementGateway.findAllByAccountIdAndMethodNotIn(accountId, CREDIT_CARD_TRANSACTIONS);

        statements.removeIf(isInvoiceCredit());

        return mapper.from(statements);
    }

    private static Predicate<BankStatement> isInvoiceCredit() {
        return statement -> statement.getCredit().getDestination().equals(Credit.Destination.INVOICE.name())
                && statement.getType().equals(TransactionType.DEPOSIT.name());
    }

    @Transactional(readOnly = true)
    public List<BankStatementOutput> findAllCardStatementsByAccountId(final String accountId) {
        final var statements = bankStatementGateway.findAllByAccountIdAndMethodIn(accountId, CREDIT_CARD_TRANSACTIONS);
        final var invoiceStatements = bankStatementGateway.findAllByAccountIdAndCreditDestinationAndType(accountId, Credit.Destination.INVOICE, TransactionType.DEPOSIT);

        statements.removeIf(isRefund());

        var bankStatements = new ArrayList<>(mapper.from(statements));
        bankStatements.addAll(mapper.from(invoiceStatements));

        return bankStatements;
    }

    private static Predicate<BankStatement> isRefund() {
        return statement -> statement.getType().equals(TransactionType.REFUND.name());
    }

    @Transactional(readOnly = true)
    public BankStatementOutput findById(final String id) {
        return mapper.from(bankStatementGateway.findById(id));
    }
}
