package com.rayllanderson.raybank.external.boleto.controllers;

import com.rayllanderson.raybank.external.boleto.BoletoRepository;
import com.rayllanderson.raybank.external.boleto.BoletoService;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/external/boletos")
public class ReceiveBoletoPaymentController {

    private final BoletoRepository boletoRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final BoletoService boletoService;

//    @PostMapping
//    @Transactional
//    public ResponseEntity<GenerateBoletoResponse> receive(@Valid @RequestBody GenerateBoletoRequest request,
//                                                          UriComponentsBuilder uriBuilder) {
//        log.info("Nova emissão de boleto recebida: {}", request);
//
//        var account = bankAccountRepository.findById(request.getId()).orElseThrow(() -> {
//            throw new NotfoundException;
//        });
//        // procurar conta
//        // creditar
//        // vlw
//        //Boleto boleto = request.toModel();
//        boletoRepository.save(boleto);
//
//        log.info("Boleto emitido com sucesso: {}", boleto);
//
//        URI uri = uriBuilder.path("/api/v1/public/external/boletos/{id}").build(boleto.getId());
//
//        return ResponseEntity.created(uri).body(GenerateBoletoResponse.fromModel(boleto));
//    }
}
