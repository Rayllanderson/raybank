package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.BoletoRepository;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BoletoPostgresGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;

    @Override
    public void save(Boleto boleto) {
        this.boletoRepository.save(boleto);
    }

    @Override
    public Boleto findByBarCode(String barCode) {
        return boletoRepository.findByBarCode(barCode)
                .orElseThrow(() -> new NotFoundException(String.format("Boleto %s was not found", barCode)));
    }
}
