package com.rayllanderson.raybank.external.token;

import com.rayllanderson.raybank.exceptions.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/public/authorization-token")
@RequiredArgsConstructor
public class RegisterExternalTokenController {

    private final ExternalAuthorizationTokenRepository externalAuthorizationTokenRepository;

    @PostMapping
    public ResponseEntity<RegisterExternalTokenResponse> registerExternalToken(@Valid @RequestBody RegisterExternalTokenRequest request) {
        log.info("Novo registro de token para: {}", request);

        var externalToken = request.toExternalToken();

        var isTokenAlreadyRegistered = externalAuthorizationTokenRepository.existsByClientName(externalToken.getClientName());
        if (isTokenAlreadyRegistered) {
            log.error("Falha ao registrar token para: {}. Motivo: Cliente já registrou o token", request.getClientName());
            throw new UnprocessableEntityException("Cliente já registrou o token");
        }

        externalAuthorizationTokenRepository.save(externalToken);

        log.info("Registro de token concluído com sucesso para: {}", request);

        var response = new RegisterExternalTokenResponse(externalToken.getToken(), externalToken.getCreationTime());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
