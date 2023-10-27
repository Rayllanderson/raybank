package com.rayllanderson.raybank.statement.services;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BankStatementFinderService {

    private final BankStatementMapper mapper;
    private final TransactionGateway transactionGateway;
    public static final List<TransactionMethod> CREDIT_CARD_TRANSACTIONS = List.of(TransactionMethod.CREDIT_CARD);

    @Transactional(readOnly = true)
    public List<BankStatement> findAllByAccountId(final String accountId) {
        List<Transaction> transactions = transactionGateway.findAllByAccountId(accountId);
        return transactions.stream().map(mapper::from).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BankStatement> findAllAccountStatementsByAccountId(final String accountId) {
        List<Transaction> transactions = transactionGateway.findAllByAccountIdAndMethodNotIn(accountId, CREDIT_CARD_TRANSACTIONS);

        transactions.removeIf(isInvoiceCredit());

        return transactions.stream().map(mapper::from).collect(Collectors.toList());
    }

    private static Predicate<Transaction> isInvoiceCredit() {
        return t -> t.getCredit().getDestination().equals(Credit.Destination.INVOICE) && t.getType().equals(TransactionType.DEPOSIT);
    }

    @Transactional(readOnly = true)
    public List<BankStatement> findAllCardStatementsByAccountId(final String accountId) {
        List<Transaction> transactions = transactionGateway.findAllByAccountIdAndMethodIn(accountId, CREDIT_CARD_TRANSACTIONS);
        final var invoiceTransactions = transactionGateway.findAllByAccountIdAndCreditDestinationAndType(accountId, Credit.Destination.INVOICE, TransactionType.DEPOSIT);

        transactions.removeIf(isRefund());

        final List<BankStatement> bankStatements = transactions.stream()
                .map(x -> mapper.from((CardCreditPaymentTransaction) x))
                .collect(Collectors.toList());

        bankStatements.addAll(invoiceTransactions.stream().map(mapper::from).collect(Collectors.toList()));

        return bankStatements;
    }

    private static Predicate<Transaction> isRefund() {
        return t -> t.getType().equals(TransactionType.REFUND);
    }

    @Transactional(readOnly = true)
    public BankStatement findById(final String id) {
        return mapper.from(transactionGateway.findById(id));
    }
}
