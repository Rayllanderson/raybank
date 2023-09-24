package com.rayllanderson.raybank.transaction.controllers;

import com.rayllanderson.raybank.security.method.RequiredStatementOwner;
import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
import com.rayllanderson.raybank.statement.services.BankStatementFinderService;
import com.rayllanderson.raybank.transaction.models.Transaction;
import com.rayllanderson.raybank.transaction.services.TransactionFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getAccountIdFrom;
import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/statements")
public class FindStatementController {

    private final TransactionFinderService transactionFinderService;

    @GetMapping
    public ResponseEntity<List<Transaction>> findAllStatements(@AuthenticationPrincipal Jwt jwt,
                                                               @RequestParam(required = false, defaultValue = "all") StatementTypeParam type) {
        final String accountId = getAccountIdFrom(jwt);
        final var statements = type.find(transactionFinderService, accountId);

        return ResponseEntity.ok(statements);
    }

    @RequiredStatementOwner
    @GetMapping("/{statementId}")
    public ResponseEntity<Transaction> findBankStatementByIdAndUserId(@PathVariable String statementId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(transactionFinderService.findById(statementId));
    }
}
