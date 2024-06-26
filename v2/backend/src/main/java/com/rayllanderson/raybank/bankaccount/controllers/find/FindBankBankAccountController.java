package com.rayllanderson.raybank.bankaccount.controllers.find;

import com.rayllanderson.raybank.bankaccount.services.find.BankAccountDetailsOutput;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountMapper;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountService;
import com.rayllanderson.raybank.bankaccount.services.find.FindAccountType;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "accounts")
@RestController
@RequestMapping("api/v1/internal/accounts")
@RequiredArgsConstructor
public class FindBankBankAccountController {

    private final FindAccountMapper findAccountMapper;
    private final FindAccountService findAccountService;

    @RequiredAccountOwner
    @GetMapping("/{accountId}")
    public ResponseEntity<?> findById(@PathVariable String accountId, @AuthenticationPrincipal Jwt jwt) {
        final BankAccountDetailsOutput output = findAccountService.findById(accountId);

        final BankAccountDetailsResponse response = findAccountMapper.from(output);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> find(@RequestParam("type") String type, @RequestParam("value") String value, @AuthenticationPrincipal Jwt jwt) {
        final var findAccountType = FindAccountType.from(type);

        final var account = findAccountService.findByType(findAccountType, value);
        final var response = findAccountMapper.mapFromBankAccount(account);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/authenticated")
    public ResponseEntity<?> findAuthenticated(@AuthenticationPrincipal Jwt jwt) {
        final BankAccountDetailsOutput output = findAccountService.findById(JwtUtils.getAccountIdFrom(jwt));

        final BankAccountDetailsResponse response = findAccountMapper.from(output);
        return ResponseEntity.ok(response);
    }
}
