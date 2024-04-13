package com.rayllanderson.raybank.pix.controllers.limit.update;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.pix.service.limit.update.UpdatePixLimitService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "pix")
@RestController
@RequestMapping("api/v1/internal/pix/limits")
@RequiredArgsConstructor
public class UpdatePixLimitController {

    private final UpdatePixLimitService service;


    @PatchMapping
    public ResponseEntity<?> update(@Valid @RequestBody UpdatePixLimitRequest request, @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        service.update(request.getNewLimit(), accountId);

        return ResponseEntity.ok().body(Map.of("new_limit", request.getNewLimit()));
    }
}
