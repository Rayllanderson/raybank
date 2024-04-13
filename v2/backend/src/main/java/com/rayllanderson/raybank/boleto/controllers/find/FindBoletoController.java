package com.rayllanderson.raybank.boleto.controllers.find;

import com.rayllanderson.raybank.boleto.services.find.BoletoDetailsOutput;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoMapper;
import com.rayllanderson.raybank.boleto.services.find.FindBoletoService;
import com.rayllanderson.raybank.core.security.keycloak.JwtUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<BoletoDetailsResponse.BoletoResponse>> findAllByAccount(@RequestParam(required = false, defaultValue = "all") String status,
                                                                                    @AuthenticationPrincipal Jwt jwt) {
        final BoletoStatusParamRequest requestParam = BoletoStatusParamRequest.from(status);

        final var boletos = findBoletoService.findAllByAccountIdAndStatus(JwtUtils.getAccountIdFrom(jwt), requestParam.getBoletoStatus());

        return ResponseEntity.ok(mapper.from(boletos));
    }
}
