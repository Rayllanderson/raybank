package com.rayllanderson.raybank.controllers.v1;

import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.bank.StatementDto;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.services.CreditCardService;
import com.rayllanderson.raybank.services.StatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/v1/users/authenticated/bank-account/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final StatementFinderService statementFinderService;

    @GetMapping
    public ResponseEntity<CreditCardDto> find(@AuthenticationPrincipal User authenticatedUser){
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCardService.findByAccountId(accountId)));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<StatementDto>> findStatements(@AuthenticationPrincipal User authenticatedUser){
        Long accountId = authenticatedUser.getBankAccount().getId();
        return ResponseEntity.ok(statementFinderService.findAllCreditCardStatements(accountId));
    }

    @PostMapping("/pay/invoice")
    public ResponseEntity<Void> payInvoice(@RequestBody @Valid com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto dto,
                                           @AuthenticationPrincipal User authenticatedUser){
        dto.setAccount(authenticatedUser.getBankAccount());
        creditCardService.payInvoice(dto);
        return ResponseEntity.noContent().build();
    }
}
