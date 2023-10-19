package com.rayllanderson.raybank.card.controllers.limit;

import com.rayllanderson.raybank.card.services.limit.ChangeCardLimitInput;
import com.rayllanderson.raybank.card.services.limit.ChangeCardLimitService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/cards/{cardId}/limit")
@RequiredArgsConstructor
public class ChangeCardLimitController {

    private final ChangeCardLimitService limitService;

    @PatchMapping
    @RequiredAccountOwner
    public ResponseEntity<?> change(@Valid @RequestBody ChangeCardLimitRequest request,
                                  @PathVariable String accountId,
                                  @PathVariable String cardId,
                                  @AuthenticationPrincipal Jwt jwt) {

        limitService.change(new ChangeCardLimitInput(cardId, request.getNewLimit()));

        return ResponseEntity.ok(Map.of("newLimit", request.getNewLimit()));
    }
}
