package com.rayllanderson.raybank.card.controllers.create;

import com.rayllanderson.raybank.card.services.create.CreateCreditCardInput;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.card.services.create.DueDays;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.rayllanderson.raybank.security.keycloak.JwtUtils.getUserIdFrom;

@RestController
@RequestMapping("api/v1/internal/credit-card")
@RequiredArgsConstructor
public class CreateCreditCardController {

    private final CreateCardService createCardService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateCreditCardRequest request,
                                    @AuthenticationPrincipal Jwt jwt) {

        final CreateCreditCardInput input = new CreateCreditCardInput(getUserIdFrom(jwt),
                request.getLimit(),
                DueDays.of(request.getDueDay()));

        final var creditCard = createCardService.createCreditCard(input);

        return ResponseEntity.status(201).body(Map.of("id", creditCard.getId()));
    }
}
