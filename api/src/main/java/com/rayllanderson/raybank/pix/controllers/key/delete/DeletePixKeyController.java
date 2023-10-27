package com.rayllanderson.raybank.pix.controllers.key.delete;

import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.core.security.method.RequiredPixKeyOwner;
import com.rayllanderson.raybank.pix.service.key.delete.DeletePixKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/internal/pix/keys")
@RequiredArgsConstructor
public class DeletePixKeyController {

    private final DeletePixKeyService service;

    @RequiredPixKeyOwner
    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteByKey(@PathVariable String key,
                                         @AuthenticationPrincipal Jwt jwt) {

        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        service.deleteByKeyAndAccount(key, accountId);

        return ResponseEntity.noContent().build();
    }
}
