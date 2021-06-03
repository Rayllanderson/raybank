package com.rayllanderson.raybank.controllers;

import com.rayllanderson.raybank.dtos.requests.bank.BankPaymentDto;
import com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.BankAccountService;
import com.rayllanderson.raybank.services.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/payment")
@RestController
public class PaymentController {

    private final BankAccountService bankAccountService;
    private final CreditCardService creditCardService;

    @PostMapping("/boleto")
    public ResponseEntity<Void> pay(@RequestBody BankPaymentDto transaction, @AuthenticationPrincipal User authenticatedUser) {
        transaction.setOwner(authenticatedUser);
        bankAccountService.pay(transaction);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/credit-card")
    public ResponseEntity<Void> purchase(@RequestBody CreditCardDto dto, @AuthenticationPrincipal User authenticatedUser) {
        dto.setAccount(authenticatedUser.getBankAccount());
        creditCardService.makePurchase(dto);
        return ResponseEntity.noContent().build();
    }

}
