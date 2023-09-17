package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.services.find.CreditCardFinderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class FindCardController {

    private final CreditCardFinderService creditCardFinderService;

    @GetMapping
    public ResponseEntity<CardDetailsResponse> find(@AuthenticationPrincipal Jwt jwt){
        final var creditCard = creditCardFinderService.findByUserId(getUserIdFrom(jwt));

        return ResponseEntity.ok(CardDetailsResponse.from(creditCard));
    }

    @GetMapping("/sensitive")
    public ResponseEntity<CardDetailsSensitiveResponse> findSensitiveData(@AuthenticationPrincipal Jwt jwt){
        final var creditCard = creditCardFinderService.findByUserId(getUserIdFrom(jwt));

        return ResponseEntity.ok(CardDetailsSensitiveResponse.from(creditCard));
    }
}
