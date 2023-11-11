package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import com.rayllanderson.raybank.bankaccount.services.transfer.BankAccountTransferMapper;
import com.rayllanderson.raybank.bankaccount.services.transfer.BankAccountTransferService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import com.rayllanderson.raybank.transaction.models.Transaction;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "accounts")
@RestController
@RequestMapping("api/v1/internal/accounts")
@RequiredArgsConstructor
public class BankAccountTransferController {

    private final BankAccountTransferService transferService;
    private final BankAccountTransferMapper transferMapper;

    @RequiredAccountOwner
    @PostMapping("/{accountId}/transfer")
    public ResponseEntity<?> transfer(@RequestBody @Valid BankAccountTransferRequest request,
                                         @PathVariable String accountId,
                                         @AuthenticationPrincipal Jwt jwt) {

        final var transfer = transferMapper.from(request, accountId);
        Transaction transaction = transferService.transfer(transfer);

        return ResponseEntity.ok(BankAccountTransferResponse.from(transaction, request.getBeneficiaryAccountNumber()));
    }
}
