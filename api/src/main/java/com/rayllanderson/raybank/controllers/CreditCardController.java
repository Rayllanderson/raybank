package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.CreateCreditCardRequest;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.CreditCardService;
import com.rayllanderson.raybank.services.TransactionFinderService;
import com.rayllanderson.raybank.services.inputs.CreateCreditCardInput;
import com.rayllanderson.raybank.services.inputs.DueDays;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final TransactionFinderService transactionFinderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCreditCardRequest request,
                                           @AuthenticationPrincipal User authenticatedUser) {
        final CreateCreditCardInput input = new CreateCreditCardInput(authenticatedUser.getBankAccount().getId(),
                request.getLimit(),
                DueDays.of(request.getDueDay()));

        final var creditCard = creditCardService.createCreditCard(input);

        return ResponseEntity.status(201).body(Map.of("id", creditCard.getId()));
    }

    @GetMapping
    public ResponseEntity<CreditCardDto> find(@AuthenticationPrincipal User authenticatedUser){
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCardService.findByAccountId(accountId)));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<TransactionDto>> findStatements(@AuthenticationPrincipal User authenticatedUser){
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(transactionFinderService.findAllCreditCardTransactions(accountId));
    }

    @PostMapping("/pay/invoice")
    public ResponseEntity<Void> payInvoice(@RequestBody @Valid com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto,
                                           @AuthenticationPrincipal User authenticatedUser){
        dto.setAccount(authenticatedUser.getBankAccount());
        creditCardService.payInvoice(dto);
        return ResponseEntity.noContent().build();
    }
}
