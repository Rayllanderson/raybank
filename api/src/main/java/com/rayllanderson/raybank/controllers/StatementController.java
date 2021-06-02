package com.rayllanderson.raybank.controllers;


import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import com.rayllanderson.raybank.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/statements")
@RestController
public class StatementController {

    private final BankStatementRepository statementRepository;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<StatementDto>> findAllStatements() {
        Long accountId = userRepository.findById(1L).get().getBankAccount().getId();
        var statements = statementRepository.findAllByAccountOwnerId(accountId)
                .stream().map(StatementDto::fromStatement).collect(Collectors.toList());
        return ResponseEntity.ok(statements);
    }
}
