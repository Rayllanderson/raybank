package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.security.method.RequiredStatementOwner;
import com.rayllanderson.raybank.statement.services.BankStatementFinderService;
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

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/internal/statements")
public class FindStatementController {

    private final BankStatementFinderService bankStatementFinderService;

    @GetMapping
    public ResponseEntity<List<BankStatementDto>> findAllStatements(@AuthenticationPrincipal Jwt jwt,
                                                                    @RequestParam(required = false, defaultValue = "all") StatementTypeParam type) {
        final String userId = getUserIdFrom(jwt);
        final var statements = type.find(bankStatementFinderService, userId);

        return ResponseEntity.ok(statements);
    }

    @RequiredStatementOwner
    @GetMapping("/{statementId}")
    public ResponseEntity<BankStatementDto> findBankStatementByIdAndUserId(@PathVariable String statementId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(bankStatementFinderService.findById(statementId));
    }
}
