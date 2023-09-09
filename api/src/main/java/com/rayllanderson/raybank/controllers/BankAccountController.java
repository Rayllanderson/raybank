package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.ContactResponseDto;
import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.services.BankAccountService;
import com.rayllanderson.raybank.services.TransactionFinderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final UserRepository userRepository;
    private final TransactionFinderService transactionFinderService;

    @GetMapping
    public ResponseEntity<BankAccountDto> findUserBankAccount(@AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return ResponseEntity.ok(bankAccountService.findByUser(authenticatedUser));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid BankTransferDto transaction,
                                         @AuthenticationPrincipal Jwt jwt) {

        transaction.setSenderId(JwtUtils.getUserIdFrom(jwt));
        bankAccountService.transfer(transaction);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid BankDepositDto transaction,
                                        @AuthenticationPrincipal Jwt jwt) {
        transaction.setOwnerId(JwtUtils.getUserIdFrom(jwt));
        bankAccountService.deposit(transaction);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/statements")
    public ResponseEntity<List<TransactionDto>> findAllStatements(@AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return ResponseEntity.ok(transactionFinderService.findAllAccountTransactions(authenticatedUser.getBankAccount()));
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<ContactResponseDto>> findAllContacts(@AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return ResponseEntity.ok(bankAccountService.findAllContactsUserId(authenticatedUser.getId()));
    }

    @GetMapping("/contacts/{id}")
    public ResponseEntity<ContactResponseDto> findContactById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {

        return ResponseEntity.ok(bankAccountService.findContactById(id, JwtUtils.getUserIdFrom(jwt)));
    }
}
