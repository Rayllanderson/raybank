package com.rayllanderson.raybank.boleto.services.find;

import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindBoletoService {

    private final BoletoGateway boletoGateway;
    private final FindBoletoMapper boletoMapper;
    private final BeneficiaryFactory beneficiaryFactory;

    @Transactional
    public BoletoDetailsOutput findByBarCode(final String barCode) {
        final Boleto boleto = boletoGateway.findByBarCode(barCode);

        final Object beneficiaryData = beneficiaryFactory.getBeneficiaryDataFrom(boleto.getBeneficiary()).getData();

        return boletoMapper.from(boleto, beneficiaryData);
    }
}
