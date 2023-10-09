package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.repositories.BoletoRepository;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BoletoPostgresGateway implements BoletoGateway {

    private final BoletoRepository boletoRepository;

    @Override
    public void save(Boleto boleto) {
        this.boletoRepository.save(boleto);
    }

    @Override
    public void saveAll(Collection<Boleto> boletos) {
        this.boletoRepository.saveAll(boletos);
    }

    @Override
    public Boleto findByBarCode(String barCode) {
        return boletoRepository.findByBarCode(barCode)
                .orElseThrow(() -> new NotFoundException(String.format("Boleto %s was not found", barCode)));
    }

    @Override
    public List<Boleto> findAllByStatus(BoletoStatus boletoStatus) {
        return boletoRepository.findAllByStatus(boletoStatus);
    }

    @Override
    public List<Boleto> findAllByExpirationDateAndStatus(LocalDate expiryDate, BoletoStatus boletoStatus) {
        return boletoRepository.findAllByExpirationDateAndStatus(expiryDate, boletoStatus);
    }
}
