package com.rayllanderson.raybank.statement.controllers;

import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.statement.services.BankStatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/statements")
@RestController
public class BankStatementController {

    private final BankStatementFinderService bankStatementService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<BankStatementDto>> findAllBankStatements(@AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(bankStatementService.findAllByAccountId(accountId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankStatementDto> findBankStatementByIdAndUserId(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(bankStatementService.findByIdAndAccountId(id, accountId));
    }
}
