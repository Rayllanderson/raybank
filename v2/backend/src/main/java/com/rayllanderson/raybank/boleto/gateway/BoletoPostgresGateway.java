package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import com.rayllanderson.raybank.boleto.repositories.BoletoRepository;
import com.rayllanderson.raybank.core.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.BOLETO_NOT_FOUND;

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
                .orElseThrow(() -> NotFoundException.withFormatted(BOLETO_NOT_FOUND, "Boleto %s não encontrado", barCode));
    }

    @Override
    public List<Boleto> findAllByStatus(BoletoStatus boletoStatus) {
        return boletoRepository.findAllByStatus(boletoStatus);
    }

    @Override
    public List<Boleto> findAllAccountId(String accountId) {
        return boletoRepository.findAllByHolderIdOrderByCreatedAtDesc(accountId);
    }

    @Override
    public List<Boleto> findAllAccountIdAndStatus(String accountId, BoletoStatus status) {
        return boletoRepository.findAllByHolderIdAndStatusOrderByCreatedAtDesc(accountId, status);
    }

    @Override
    public List<Boleto> findAllByExpirationDateAndStatus(LocalDate expiryDate, BoletoStatus boletoStatus) {
        return boletoRepository.findAllByExpirationDateLessThanEqualAndStatus(expiryDate, boletoStatus);
    }
}
