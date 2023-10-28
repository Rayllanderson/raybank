package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.core.security.method.RequiredStatementOwner;
import com.rayllanderson.raybank.statement.services.find.BankStatementFinderService;
import com.rayllanderson.raybank.statement.services.find.BankStatementMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "statements")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/accounts/{accountId}/statements")
public class FindStatementController {

    private final BankStatementMapper mapper;
    private final BankStatementFinderService bankStatementFinderService;
//todo:: paginacao
    @GetMapping
    @RequiredAccountOwner
    public ResponseEntity<?> findAllStatements(@AuthenticationPrincipal Jwt jwt,
                                               @PathVariable String accountId,
                                               @RequestParam(required = false, defaultValue = "all") StatementTypeParam type) {

        final var statements = type.find(bankStatementFinderService, accountId);

        return ResponseEntity.ok(mapper.toResponse(statements));
    }

    @RequiredStatementOwner
    @GetMapping("/{statementId}")
    public ResponseEntity<?> findBankStatementById(@PathVariable String accountId,
                                                   @PathVariable String statementId,
                                                   @AuthenticationPrincipal Jwt jwt) {
        final var statement = bankStatementFinderService.findById(statementId);
        return ResponseEntity.ok(mapper.toResponse(statement));
    }
}
