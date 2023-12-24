package com.rayllanderson.raybank.boleto.services.expire;

import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BoletoExpirationService {

    private final BoletoGateway boletoGateway;

    public void expire() {
        final var yesterday = LocalDate.now().minusDays(1);
        final var expiredBoletos = boletoGateway.findAllByExpirationDateAndStatus(yesterday, BoletoStatus.WAITING_PAYMENT);

        expiredBoletos.forEach(Boleto::expire);

        boletoGateway.saveAll(expiredBoletos);
    }

}
