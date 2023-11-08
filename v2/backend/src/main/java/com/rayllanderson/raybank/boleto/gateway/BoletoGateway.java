package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface BoletoGateway {
    void save(final Boleto boleto);
    void saveAll(final Collection<Boleto> boletos);
    Boleto findByBarCode(final String barCode);
    List<Boleto> findAllByStatus(final BoletoStatus boletoStatus);
    List<Boleto> findAllAccountId(final String accountId);
    List<Boleto> findAllAccountIdAndStatus(final String accountId, BoletoStatus status);
    List<Boleto> findAllByExpirationDateAndStatus(final LocalDate expiryDate, final BoletoStatus boletoStatus);

}
