package com.rayllanderson.raybank.boleto.controllers.find;

import com.rayllanderson.raybank.boleto.services.find.BoletoDetailsOutput;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoMapper;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "boletos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/internal/boletos")
public class FindBoletoController {

    private final FindBoletoMapper mapper;
    private final FindBoletoService findBoletoService;

    @GetMapping("/{barCode}")
    public ResponseEntity<BoletoDetailsResponse> find(@PathVariable String barCode, @AuthenticationPrincipal Jwt jwt) {

        final BoletoDetailsOutput boleto = findBoletoService.findByBarCode(barCode);

        return ResponseEntity.ok(mapper.from(boleto));
    }
}
