package com.rayllanderson.raybank.bankaccount.controllers;

import com.rayllanderson.raybank.bankaccount.controllers.requests.BankPaymentDto;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.bankaccount.services.BankAccountService;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/users/authenticated/payment")
@RestController
public class PaymentController {
    //todo::ajustar

    private final BankAccountService bankAccountService;
    private final CreateCardService createCardService;

    @PostMapping("/boleto")
    public ResponseEntity<Void> pay(@RequestBody @Valid BankPaymentDto bankStatement, @AuthenticationPrincipal Jwt jwt) {
        bankStatement.setOwnerId(JwtUtils.getUserIdFrom(jwt));
        bankAccountService.pay(bankStatement);
        return ResponseEntity.noContent().build();
    }
}
