package com.rayllanderson.raybank.card.controllers.find;

import com.rayllanderson.raybank.card.services.find.CardFinderService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountAndCardOwner;
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

@Tag(name = "cards")
@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/cards")
@RequiredArgsConstructor
public class FindCardController {

    private final CardFinderService cardFinderService;

    @RequiredAccountAndCardOwner
    @GetMapping("/{cardId}")
    public ResponseEntity<?> find(@PathVariable String accountId,
                                  @PathVariable String cardId,
                                  @AuthenticationPrincipal Jwt jwt,
                                  @RequestParam(required = false, defaultValue = "false") Boolean sensitive) {
        final var creditCard = cardFinderService.findById(cardId);

        if (Boolean.TRUE.equals(sensitive))
            return ResponseEntity.ok(CardDetailsSensitiveResponse.from(creditCard));

        return ResponseEntity.ok(CardDetailsResponse.from(creditCard));
    }
}
