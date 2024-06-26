package com.rayllanderson.raybank.boleto.controllers.generate;

import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoInput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoOutput;
import com.rayllanderson.raybank.boleto.services.generate.GenerateBoletoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Tag(name = "boletos")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/internal/boletos")
public class GenerateBoletoController {

    private final GenerateBoletoService generateBoletoService;
    private final GenerateBoletoMapper mapper;

    @PostMapping
    public ResponseEntity<GenerateBoletoResponse> generateBoleto(@Valid @RequestBody GenerateBoletoRequest request,
                                                                 @AuthenticationPrincipal Jwt jwt,
                                                                 UriComponentsBuilder uriBuilder) {

        final GenerateBoletoInput generateBoletoInput = mapper.from(request);
        final GenerateBoletoOutput boletoOutput = generateBoletoService.generate(generateBoletoInput);

        final URI uri = uriBuilder.path("/api/v1/internal/boletos/{id}").build(boletoOutput.getBarCode());
        final GenerateBoletoResponse response = mapper.from(boletoOutput);

        return ResponseEntity.created(uri).body(response);
    }
}
