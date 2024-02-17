package com.rayllanderson.raybank.statement.services.find;

import com.rayllanderson.raybank.statement.gateway.BankStatementGateway;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import io.github.rayexpresslibraries.ddd.domain.pagination.query.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.rayllanderson.raybank.statement.services.find.BankStatementSorter.sortByProperty;

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
    public Pagination<BankStatementOutput> findAllByAccountId(final String accountId, SearchQuery query) {
        var statements = bankStatementGateway.findAllByAccountId(accountId, query);
        return statements.map(mapper::from);
    }

    @Transactional(readOnly = true)
    public List<BankStatementOutput> findAllAccountStatementsByAccountId(final String accountId) {
        var statements = bankStatementGateway.findAllByAccountIdAndMethodNotIn(accountId, CREDIT_CARD_TRANSACTIONS);

        statements.removeIf(isInvoiceCredit());

        return mapper.from(statements);
    }

    @Transactional(readOnly = true)
    public Pagination<BankStatementOutput> findAllAccountStatementsByAccountId(final String accountId, SearchQuery query) {
        var statements = bankStatementGateway.findAllByAccountIdAndMethodNotIn(accountId, query, CREDIT_CARD_TRANSACTIONS);

        statements.items().removeIf(isInvoiceCredit());

        return statements.map(mapper::from);
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

    @Transactional(readOnly = true)
    public Pagination<BankStatementOutput> findAllCardStatementsByAccountId(final String accountId, SearchQuery query) {
        final var statements = bankStatementGateway.findAllByAccountIdAndMethodIn(accountId, query, CREDIT_CARD_TRANSACTIONS);
        final var invoiceStatements = bankStatementGateway.findAllByAccountIdAndCreditDestinationAndType(accountId, query, Credit.Destination.INVOICE, TransactionType.DEPOSIT);

        statements.items().removeIf(isRefund());

        Pagination<BankStatementOutput> bankStatements = statements.map(mapper::from);
        Pagination<BankStatementOutput> invoiceBankStatements = invoiceStatements.map(mapper::from);

        List<BankStatementOutput> allBankStatements = new ArrayList<>(bankStatements.items());
        allBankStatements.addAll(invoiceBankStatements.items());

        sortByProperty(allBankStatements, query.sortProperty(), query.sort().direction());

        return new Pagination<>(bankStatements.page(), bankStatements.size(), bankStatements.total(), allBankStatements);
    }

    private static Predicate<BankStatement> isRefund() {
        return statement -> statement.getType().equals(TransactionType.REFUND.name());
    }

    @Transactional(readOnly = true)
    public BankStatementOutput findById(final String id) {
        return mapper.from(bankStatementGateway.findById(id));
    }
}
