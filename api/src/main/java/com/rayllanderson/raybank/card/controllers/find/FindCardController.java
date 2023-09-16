package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.models.CreditCard;
import com.rayllanderson.raybank.card.services.find.CreditCardFinderService;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
import com.rayllanderson.raybank.statement.services.BankStatementFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class FindCardController {

    private final CreateCardService createCardService;
    private final CreditCardFinderService creditCardFinderService;
    private final BankStatementFinderService bankStatementFinderService;

    @GetMapping
    public ResponseEntity<CreditCardDto> find(@AuthenticationPrincipal Jwt jwt){
        final CreditCard creditCard = creditCardFinderService.findByUserId(getUserIdFrom(jwt));

        return ResponseEntity.ok(CreditCardDto.fromCreditCard(creditCard));
    }

    @GetMapping("/sensitive")
    public ResponseEntity<CreditCardSensitiveDataResponse> findSensitiveData(@AuthenticationPrincipal Jwt jwt){
        final CreditCard creditCard = creditCardFinderService.findByUserId(getUserIdFrom(jwt));

        return ResponseEntity.ok(CreditCardSensitiveDataResponse.fromCreditCard(creditCard));
    }

    @GetMapping("/invoices")
    public ResponseEntity<CreditCardDetailsResponse> findCardInvoices(@AuthenticationPrincipal Jwt jwt){
        final CreditCard creditCard = creditCardFinderService.findByUserId(getUserIdFrom(jwt));

        return ResponseEntity.ok(CreditCardDetailsResponse.fromCreditCard(creditCard));
    }

    @GetMapping("/statements")
    public ResponseEntity<List<BankStatementDto>> findStatements(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(bankStatementFinderService.findAllCreditCardBankStatementsByUserId(getUserIdFrom(jwt)));
    }
}
