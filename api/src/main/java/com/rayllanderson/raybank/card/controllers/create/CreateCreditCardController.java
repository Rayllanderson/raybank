package com.rayllanderson.raybank.card.controllers.create;

import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.card.services.create.CreateCreditCardInput;
import com.rayllanderson.raybank.card.services.create.DueDays;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
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

import java.util.Map;

@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/cards")
@RequiredArgsConstructor
public class CreateCreditCardController {

    private final CreateCardService createCardService;

    @PostMapping
    @RequiredAccountOwner
    public ResponseEntity<?> create(@PathVariable String accountId,
                                    @RequestBody @Valid CreateCreditCardRequest request,
                                    @AuthenticationPrincipal Jwt jwt) {

        final CreateCreditCardInput input = new CreateCreditCardInput(accountId,
                request.getLimit(),
                DueDays.of(request.getDueDay()));

        final var creditCard = createCardService.createCreditCard(input);

        return ResponseEntity.status(201).body(Map.of("id", creditCard.getId()));
    }
}
