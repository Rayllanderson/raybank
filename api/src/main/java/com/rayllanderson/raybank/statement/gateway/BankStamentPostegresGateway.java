package com.rayllanderson.raybank.statement.gateway;

import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.repositories.BankStatementRepository;
import com.rayllanderson.raybank.transaction.models.Credit;
import com.rayllanderson.raybank.transaction.models.TransactionMethod;
import com.rayllanderson.raybank.transaction.models.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.STATAMENT_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class BankStamentPostegresGateway implements BankStatementGateway {
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
    public BankStatement save(BankStatement transaction) {
        return this.bankStatementRepository.save(transaction);
    }

    @Override
    public List<BankStatement> findAllByAccountIdAndMethodNotIn(String accountId, List<TransactionMethod> methods) {
        return bankStatementRepository.findAllByAccountIdAndMethodNotIn(accountId, enumListToString(methods));
    }

    @Override
    public List<BankStatement> findAllByAccountIdAndMethodIn(String accountId, List<TransactionMethod> methods) {
        return bankStatementRepository.findAllByAccountIdAndMethodIn(accountId, enumListToString(methods));
    }

    @Override
    public List<BankStatement> findAllByAccountIdAndCreditDestinationAndType(String accountId, Credit.Destination destination, TransactionType type) {
        return bankStatementRepository.findAllByAccountIdAndCreditDestinationAndType(accountId, destination.name(), type.name());
    }

    private List<String> enumListToString(List<TransactionMethod> m) {
        return m.stream().map(TransactionMethod::name).collect(Collectors.toList());
    }
}
