package com.rayllanderson.raybank.statement.services;


import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
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
    public List<BankStatementDto> findAllByUserId(final String userId) {
        return bankStatementRepository.findAllByAccountOwnerUserId(userId)
                .stream()
                .map(BankStatementDto::fromBankStatement)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BankStatementDto> findAllAccountStatementsByUserId(final String userId){
        List<BankStatement> accountStatements = bankStatementRepository.findAllByAccountOwnerUserIdAndTypeNot(
                userId, BankStatementType.CREDIT_CARD_PAYMENT);
        return accountStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BankStatementDto> findAllCardStatementsByUserId(String userId){
        var creditCardStatements = bankStatementRepository.findAllByAccountOwnerUserIdAndType(
                userId, BankStatementType.CREDIT_CARD_PAYMENT
        );
        creditCardStatements.addAll(bankStatementRepository.findAllByAccountOwnerUserIdAndType(
                userId, BankStatementType.INVOICE_PAYMENT));
        return creditCardStatements.stream().map(BankStatementDto::fromBankStatement).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BankStatementDto findById(final String id) {
        final var bankStatement = bankStatementRepository.findById(id).orElseThrow(() -> new BadRequestException(
                "Extrato não encontrado"));
        return BankStatementDto.fromBankStatement(bankStatement);
    }
}
