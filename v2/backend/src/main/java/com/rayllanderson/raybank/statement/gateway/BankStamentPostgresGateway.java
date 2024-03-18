package com.rayllanderson.raybank.statement.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repositories.BankStatementRepository;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import io.github.rayexpresslibraries.ddd.domain.pagination.Pagination;
import io.github.rayexpresslibraries.ddd.domain.pagination.query.SearchQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.STATAMENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BankStamentPostgresGateway implements BankStatementGateway {
    private final BankStatementRepository bankStatementRepository;

    @Override
    public BankStatement findById(String id) {
        return bankStatementRepository.findById(id)
                .orElseThrow(() -> NotFoundException.with(STATAMENT_NOT_FOUND, "Nenhum extrato foi encontrado"));
    }

    @Override
    public List<BankStatement> findAllByAccountId(String accountId) {
        return bankStatementRepository.findAllByAccountId(accountId);
    }

    @Override
    public Pagination<BankStatement> findAllByAccountId(String accountId, SearchQuery query) {
        final PageRequest pageRequest = toPageRequest(query);

        Page<BankStatement> page;

        if (query.searchAttributeValue() == null) {
            page = bankStatementRepository.findAllByAccountId(accountId, pageRequest);
        } else {
            page = bankStatementRepository.searchByTermAndAccountId(query.searchAttributeValue(), accountId, pageRequest);
        }

        return toPagination(page);
    }

    @Override
    public BankStatement save(BankStatement transaction) {
        return this.bankStatementRepository.save(transaction);
    }

    @Override
    public Pagination<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, SearchQuery query, List<TransactionMethod> methods) {
        final PageRequest pageRequest = toPageRequest(query);
        Page<BankStatement> page;
        if (query.searchAttributeValue() == null)
            page = bankStatementRepository.findAllByAccountIdAndMethodNotIn(accountId, enumListToString(methods), pageRequest);
        else
            page = bankStatementRepository.searchByTermAndAccountIdAndMethodNotIn(query.searchAttributeValue(), accountId, enumListToString(methods), pageRequest);
        return toPagination(page);
    }

    @Override
    public Pagination<BankStatement> findAllByAccountIdAndMethodIn(String accountId, SearchQuery query, List<TransactionMethod> methods) {
        final PageRequest pageRequest = toPageRequest(query);
        Page<BankStatement> page;
        if (query.searchAttributeValue() == null)
            page = bankStatementRepository.findAllByAccountIdAndMethodIn(accountId, enumListToString(methods), pageRequest);
        else
            page = bankStatementRepository.searchByTermAndAccountIdAndMethodIn(query.searchAttributeValue(), accountId, enumListToString(methods), pageRequest);
        return toPagination(page);
    }

    @Override
    public Pagination<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, SearchQuery query, Credit.Destination destination, TransactionType type) {
        final PageRequest pageRequest = toPageRequest(query);
        Page<BankStatement> page;
        if (query.searchAttributeValue() == null)
            page = bankStatementRepository.findAllByAccountIdAndCreditDestinationAndType(accountId, destination.name(), type.name(), pageRequest);
        else
            page = bankStatementRepository.searchByTermAndAccountIdAndCreditDestinationAndType(query.searchAttributeValue(), accountId, destination.name(), type.name(), pageRequest);
        return toPagination(page);
    }

    private List<String> enumListToString(List<TransactionMethod> m) {
        return m.stream().map(TransactionMethod::name).toList();
    }

    private static PageRequest toPageRequest(SearchQuery query) {
        return PageRequest.of(query.currentPage(), query.pageSize(), Sort.by(Sort.Direction.fromString(query.sortDirection()), query.sortProperty()));
    }

    private static Pagination<BankStatement> toPagination(Page<BankStatement> page) {
        return new Pagination<>(page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                new ArrayList<>(page.getContent()));
    }
}
