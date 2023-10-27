package com.rayllanderson.raybank.pix.controllers.key.register;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import com.rayllanderson.raybank.pix.model.key.PixKeyType;
import com.rayllanderson.raybank.pix.service.key.register.RegisterPixKeyInput;
import com.rayllanderson.raybank.pix.service.key.register.RegisterPixKeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@Tag(name = "pix")
@RestController
@RequestMapping("api/v1/internal/pix/keys")
@RequiredArgsConstructor
public class RegisterPixKeyController {

    private final RegisterPixKeyService registerKeyService;

    @PostMapping
    public ResponseEntity<RegisterPixResponse> register(@RequestBody @Valid RegisterPixKeyRequest request, @AuthenticationPrincipal Jwt jwt) {
        final var accountId = JwtUtils.getAccountIdFrom(jwt);

        final var key = new RegisterPixKeyInput(request.getKey(), getKeytype(request.getType()), accountId);
        final var registeredKey = registerKeyService.register(key);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RegisterPixResponse.from(registeredKey, request.getType()));
    }

    private static PixKeyType getKeytype(String keyType) {
        try {
            return PixKeyType.valueOf(keyType.toUpperCase());
        } catch (Exception e) {
            throw new BadRequestException("Tipo de chave inválido. Tipos permitidos são: " + Arrays.toString(PixKeyType.values()));
        }
    }
}
