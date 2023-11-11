package com.rayllanderson.raybank.bankaccount.controllers.deposit;

import com.rayllanderson.raybank.bankaccount.services.deposit.DepositAccountInput;
import com.rayllanderson.raybank.bankaccount.services.deposit.DepositAccountService;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "accounts")
@RestController
@RequestMapping("api/v1/internal/accounts")
@RequiredArgsConstructor
public class BankBankAccountDepositController {

    private final DepositAccountService depositService;

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody @Valid BankAccountDepositRequest request,
                                     @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);
        final var transaction = depositService.deposit(new DepositAccountInput(accountId, request.getAmount()));

        return ResponseEntity.ok(BankAccountDepositResponse.from(transaction));
    }
}
