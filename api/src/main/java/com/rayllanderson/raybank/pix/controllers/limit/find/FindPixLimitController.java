package com.rayllanderson.raybank.pix.controllers.limit.find;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.pix.service.limit.find.FindPixLimitOutput;
import com.rayllanderson.raybank.pix.service.limit.find.FindPixLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/pix/limits")
@RequiredArgsConstructor
public class FindPixLimitController {

    private final FindPixLimitService service;

    @GetMapping
    public ResponseEntity<?> findLimit(@AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        FindPixLimitOutput output = service.findByAccountId(accountId);

        return ResponseEntity.ok().body(new FindPixLimitResponse(output.getLimit()));
    }
}
