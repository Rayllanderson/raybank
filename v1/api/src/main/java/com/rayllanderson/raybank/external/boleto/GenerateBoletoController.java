package com.rayllanderson.raybank.external.boleto;

import com.rayllanderson.raybank.external.boleto.model.Boleto;
import com.rayllanderson.raybank.external.boleto.requests.GenerateBoletoRequest;
import com.rayllanderson.raybank.external.boleto.response.GenerateBoletoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/public/external/boletos")
public class GenerateBoletoController {

    private final BoletoRepository boletoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<GenerateBoletoResponse> generateBoleto(@Valid @RequestBody GenerateBoletoRequest request,
                                                                 UriComponentsBuilder uriBuilder) {
        log.info("Nova emissão de boleto recebida: {}", request);

        Boleto boleto = request.toModel();
        boletoRepository.save(boleto);

        log.info("Boleto emitido com sucesso: {}", boleto);

        URI uri = uriBuilder.path("/api/v1/public/external/boletos/{id}").build(boleto.getId());

        return ResponseEntity.created(uri).body(GenerateBoletoResponse.fromModel(boleto));
    }
}
