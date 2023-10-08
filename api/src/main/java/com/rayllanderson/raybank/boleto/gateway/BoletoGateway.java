package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;

import java.util.List;

public interface BoletoGateway {
    void save(final Boleto boleto);
    Boleto findByBarCode(final String barCode);

    List<Boleto> findAllByStatus(final BoletoStatus boletoStatus);

}
