package com.rayllanderson.raybank.boleto.controllers.find;

import com.rayllanderson.raybank.boleto.services.find.BoletoDetailsOutput;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoMapper;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/external/boletos")
public class FindBoletoController {

    private final FindBoletoMapper mapper;
    private final FindBoletoService findBoletoService;

    @GetMapping("/{barCode}")
    public ResponseEntity<BoletoDetailsResponse> find(@PathVariable String barCode, @AuthenticationPrincipal Jwt jwt) {

        final BoletoDetailsOutput boleto = findBoletoService.findByBarCode(barCode);

        return ResponseEntity.ok(mapper.from(boleto));
    }
}