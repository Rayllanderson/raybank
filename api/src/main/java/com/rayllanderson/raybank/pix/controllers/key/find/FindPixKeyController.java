package com.rayllanderson.raybank.pix.controllers.key.find;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.core.security.method.RequiredPixKeyOwner;
import com.rayllanderson.raybank.pix.service.key.find.FindPixKeyMapper;
import com.rayllanderson.raybank.pix.service.key.find.FindPixKeyOutput;
import com.rayllanderson.raybank.pix.service.key.find.FindPixKeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "pix")
@RestController
@RequestMapping("api/v1/internal/pix/keys")
@RequiredArgsConstructor
public class FindPixKeyController {

    private final FindPixKeyMapper mapper;
    private final FindPixKeyService service;

    @GetMapping
    public ResponseEntity<FindListPixKeyResponse> findAllByAccountId(@AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        final var keys = service.findAllByAccountId(accountId);

        return ResponseEntity.ok().body(FindListPixKeyResponse.from(mapper.from(keys)));
    }

    @RequiredPixKeyOwner
    @GetMapping("/{key}")
    public ResponseEntity<FindPixKeyResponse> findByKey(@PathVariable String key, @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        final FindPixKeyOutput pixKey = service.findByKeyAndAccountId(key, accountId);

        return ResponseEntity.ok().body(mapper.from(pixKey));
    }
}
