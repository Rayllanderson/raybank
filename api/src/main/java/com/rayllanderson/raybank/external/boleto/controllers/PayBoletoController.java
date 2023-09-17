package com.rayllanderson.raybank.external.boleto.controllers;

import com.rayllanderson.raybank.external.boleto.BoletoService;
import com.rayllanderson.raybank.external.boleto.requests.PayBoletoRequest;
import com.rayllanderson.raybank.external.boleto.response.GenerateBoletoResponse;
import com.rayllanderson.raybank.users.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/external/boletos")
public class PayBoletoController {


    private final BoletoService boletoService;

    @Transactional
    @PatchMapping
    public ResponseEntity<GenerateBoletoResponse> receive(@Valid @RequestBody PayBoletoRequest request,
                                                          @AuthenticationPrincipal User user,
                                                          UriComponentsBuilder uriBuilder) {
        request.setOwnerId(user.getId());
        log.info("Realizando pagamento para boleto: {}", request);
        boletoService.pay(request);
        return ResponseEntity.ok(null);
    }

}
