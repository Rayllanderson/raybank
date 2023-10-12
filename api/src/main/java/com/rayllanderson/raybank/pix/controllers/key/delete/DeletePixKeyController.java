package com.rayllanderson.raybank.pix.controllers.key.delete;

import com.rayllanderson.raybank.core.security.method.RequiredAccountOwner;
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
@RequestMapping("api/v1/internal/accounts/{accountId}/pix/keys")
@RequiredArgsConstructor
public class DeletePixKeyController {

    private final DeletePixKeyService service;

    @RequiredAccountOwner
    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteByKey(@PathVariable String accountId,
                                         @PathVariable String key,
                                         @AuthenticationPrincipal Jwt jwt) {

        service.deleteByKeyAndAccount(key, accountId);

        return ResponseEntity.noContent().build();
    }
}
