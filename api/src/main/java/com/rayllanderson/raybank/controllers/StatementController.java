package com.rayllanderson.raybank.controllers;


import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.StatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/statements")
@RestController
public class StatementController {

    private final StatementFinderService statementService;

    @GetMapping
    public ResponseEntity<List<StatementDto>> findAllStatements(@AuthenticationPrincipal User authenticatedUser) {
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(statementService.findAllByAccountId(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatementDto> findStatementById(@PathVariable Long id, @AuthenticationPrincipal User authenticatedUser) {
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(statementService.findByIdAndAccountId(id, accountId));
    }
}
