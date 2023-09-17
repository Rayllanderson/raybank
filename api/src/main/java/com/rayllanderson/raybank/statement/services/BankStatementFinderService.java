package com.rayllanderson.raybank.statement.services;


import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.statement.models.BankStatementType;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankStatementFinderService {

    private final BankStatementRepository bankStatementRepository;

    @Transactional(readOnly = true)
    public List<BankStatementDto> findAllByAccountId(Long accountId) {
        return bankStatementRepository.findAllByAccountOwnerId(accountId).stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BankStatementDto findByIdAndAccountId(String id, Long accountId) {
        var bankStatement = bankStatementRepository.findByIdAndAccountOwnerId(id, accountId).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
        return BankStatementDto.fromBankStatement(bankStatement);
    }

    @Transactional(readOnly = true)
    public List<BankStatementDto> findAllAccountBankStatements(BankAccount account){
        List<BankStatement> accountStatements = bankStatementRepository.findAllByAccountOwnerIdAndTypeNot(
                account.getId(), BankStatementType.CREDIT_CARD_PAYMENT);
        return accountStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BankStatementDto> findAllCreditCardBankStatementsByUserId(String userId){
        var creditCardStatements = bankStatementRepository.findAllByAccountOwnerUserIdAndType(
                userId, BankStatementType.CREDIT_CARD_PAYMENT
        );
        creditCardStatements.addAll(bankStatementRepository.findAllByAccountOwnerUserIdAndType(
                userId, BankStatementType.INVOICE_PAYMENT));
        return creditCardStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }
}
