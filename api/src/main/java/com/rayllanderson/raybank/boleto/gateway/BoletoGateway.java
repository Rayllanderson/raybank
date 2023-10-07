package com.rayllanderson.raybank.boleto.gateway;

import com.rayllanderson.raybank.boleto.models.Boleto;

public interface BoletoGateway {
    void save(final Boleto boleto);
    Boleto findByBarCode(final String barCode);

}
