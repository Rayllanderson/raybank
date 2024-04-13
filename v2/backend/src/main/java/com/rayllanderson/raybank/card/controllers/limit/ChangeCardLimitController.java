package com.rayllanderson.raybank.card.controllers.limit;

import com.rayllanderson.raybank.card.services.limit.ChangeCardLimitInput;
import com.rayllanderson.raybank.card.services.limit.ChangeCardLimitService;
import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "cards")
@RestController
@RequestMapping("api/v1/internal/accounts/{accountId}/cards/limit")
@RequiredArgsConstructor
public class ChangeCardLimitController {

    private final ChangeCardLimitService limitService;

    @PatchMapping
    @RequiredAccountOwner
    public ResponseEntity<?> change(@Valid @RequestBody ChangeCardLimitRequest request,
                                  @PathVariable String accountId,
                                  @AuthenticationPrincipal Jwt jwt) {

        limitService.change(new ChangeCardLimitInput(accountId, request.getLimit()));

        return ResponseEntity.ok(Map.of("new_limit", request.getLimit()));
    }
}
