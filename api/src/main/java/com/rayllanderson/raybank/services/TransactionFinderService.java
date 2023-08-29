package com.rayllanderson.raybank.services;


import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.Transaction;
import com.rayllanderson.raybank.models.TransactionType;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class StatementFinderService {

    private final BankStatementRepository statementRepository;

    @Transactional(readOnly = true)
    public List<StatementDto> findAllByAccountId(Long accountId) {
        return statementRepository.findAllByAccountOwnerId(accountId).stream().map(StatementDto::fromStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StatementDto findByIdAndAccountId(Long id, Long accountId) {
        var transaction = statementRepository.findByIdAndAccountOwnerId(id, accountId).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
        return StatementDto.fromStatement(transaction);
    }

    @Transactional(readOnly = true)
    public List<StatementDto> findAllAccountStatements(BankAccount account){
        List<Transaction> accountStatements = statementRepository.findAllByAccountOwnerIdAndStatementTypeNot(
                account.getId(), TransactionType.CREDIT_CARD_PAYMENT);
        return accountStatements.stream().map(StatementDto::fromStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<StatementDto> findAllCreditCardStatements(Long accountOwnerId){
        var creditCardStatements = statementRepository.findAllByAccountOwnerIdAndStatementType(
                accountOwnerId, TransactionType.CREDIT_CARD_PAYMENT
        );
        creditCardStatements.addAll(statementRepository.findAllByAccountOwnerIdAndStatementType(
                accountOwnerId, TransactionType.INVOICE_PAYMENT));
        return creditCardStatements.stream().map(StatementDto::fromStatement).collect(Collectors.toList());
    }
}
