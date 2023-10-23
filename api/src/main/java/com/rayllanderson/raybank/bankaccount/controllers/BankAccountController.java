package com.rayllanderson.raybank.bankaccount.controllers;

import com.rayllanderson.raybank.bankaccount.controllers.reponses.BankAccountDto;
import com.rayllanderson.raybank.bankaccount.controllers.reponses.ContactResponseDto;
import com.rayllanderson.raybank.bankaccount.controllers.requests.BankDepositDto;
import com.rayllanderson.raybank.bankaccount.controllers.requests.BankTransferDto;
import com.rayllanderson.raybank.bankaccount.services.BankAccountService;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.users.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<BankAccountDto> findUserBankAccount(@AuthenticationPrincipal Jwt jwt) {
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        return ResponseEntity.ok(bankAccountService.findByUser(authenticatedUser));
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody @Valid BankTransferDto bankStatement,
                                         @AuthenticationPrincipal Jwt jwt) {

        bankStatement.setSenderId(JwtUtils.getUserIdFrom(jwt));
        bankAccountService.transfer(bankStatement);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody @Valid BankDepositDto bankStatement,
                                        @AuthenticationPrincipal Jwt jwt) {
        bankStatement.setOwnerId(JwtUtils.getUserIdFrom(jwt));
        bankAccountService.deposit(bankStatement);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping("/contacts")
//    public ResponseEntity<List<ContactResponseDto>> findAllContacts(@AuthenticationPrincipal Jwt jwt) {
//        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
//        return ResponseEntity.ok(bankAccountService.findAllContactsUserId(authenticatedUser.getId()));
//    }
//
//    @GetMapping("/contacts/{id}")
//    public ResponseEntity<ContactResponseDto> findContactById(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
//
//        return ResponseEntity.ok(bankAccountService.findContactById(id, JwtUtils.getUserIdFrom(jwt)));
//    }
}
