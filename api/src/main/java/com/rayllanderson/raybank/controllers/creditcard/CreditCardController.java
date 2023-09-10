package com.rayllanderson.raybank.controllers.creditcard;

import com.rayllanderson.raybank.controllers.creditcard.requests.CreateCreditCardRequest;
import com.rayllanderson.raybank.controllers.creditcard.responses.CreditCardDetailsResponse;
import com.rayllanderson.raybank.controllers.creditcard.responses.CreditCardSensitiveDataResponse;
import com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.responses.bank.TransactionDto;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.services.TransactionFinderService;
import com.rayllanderson.raybank.services.creditcard.CreditCardFinderService;
import com.rayllanderson.raybank.services.creditcard.CreditCardService;
import com.rayllanderson.raybank.services.creditcard.inputs.CreateCreditCardInput;
import com.rayllanderson.raybank.services.creditcard.inputs.DueDays;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.*;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreditCardService creditCardService;
    private final CreditCardFinderService creditCardFinderService;
    private final TransactionFinderService transactionFinderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCreditCardRequest request,
                                    @AuthenticationPrincipal Jwt jwt) {

        final CreateCreditCardInput input = new CreateCreditCardInput(getUserIdFrom(jwt),
                request.getLimit(),
                DueDays.of(request.getDueDay()));

        final var creditCard = creditCardService.createCreditCard(input);

        return ResponseEntity.status(201).body(Map.of("id", creditCard.getId()));
    }

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
    public ResponseEntity<List<TransactionDto>> findStatements(@AuthenticationPrincipal Jwt jwt){
        return ResponseEntity.ok(transactionFinderService.findAllCreditCardTransactionsByUserId(getUserIdFrom(jwt)));
    }

//    @PostMapping("/invoice")
//    public ResponseEntity<Void> payInvoice(@RequestBody @Valid com.rayllanderson.raybank.controllers.v2.creditcard.CreditCardDto dto,
//                                           @AuthenticationPrincipal Jwt jwt){
//        var authenticatedUser = userRepository.findById(getUserIdFrom(jwt))
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
//        dto.setAccount(authenticatedUser.getBankAccount());
//        creditCardService.payInvoice(dto);
//        return ResponseEntity.noContent().build();
//    }
}
