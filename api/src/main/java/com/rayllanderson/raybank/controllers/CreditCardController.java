package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.CreateCreditCardRequest;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.repositories.UserRepository;
import com.rayllanderson.raybank.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.services.TransactionFinderService;
import com.rayllanderson.raybank.services.creditcard.CreditCardService;
import com.rayllanderson.raybank.services.creditcard.inputs.CreateCreditCardInput;
import com.rayllanderson.raybank.services.creditcard.inputs.DueDays;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final UserRepository userRepository;
    private final TransactionFinderService transactionFinderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCreditCardRequest request,
                                    @AuthenticationPrincipal Jwt jwt) {

        var user = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));

        final CreateCreditCardInput input = new CreateCreditCardInput(user.getBankAccount().getId(),
                request.getLimit(),
                DueDays.of(request.getDueDay()));

        final var creditCard = creditCardService.createCreditCard(input);

        return ResponseEntity.status(201).body(Map.of("id", creditCard.getId()));
    }

    @GetMapping
    public ResponseEntity<CreditCardDto> find(@AuthenticationPrincipal Jwt jwt){
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCardService.findByAccountId(accountId)));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<TransactionDto>> findStatements(@AuthenticationPrincipal Jwt jwt){
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        return ResponseEntity.ok(transactionFinderService.findAllCreditCardTransactions(authenticatedUser.getBankAccount().getId()));
    }

    @PostMapping("/pay/invoice")
    public ResponseEntity<Void> payInvoice(@RequestBody @Valid com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto,
                                           @AuthenticationPrincipal Jwt jwt){
        var authenticatedUser = userRepository.findById(JwtUtils.getUserIdFrom(jwt))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        dto.setAccount(authenticatedUser.getBankAccount());
        creditCardService.payInvoice(dto);
        return ResponseEntity.noContent().build();
    }
}
