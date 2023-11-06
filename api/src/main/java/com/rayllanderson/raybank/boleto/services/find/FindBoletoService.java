package com.rayllanderson.raybank.boleto.services.find;

import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public List<BoletoDetailsOutput.BoletoData> findAllByAccountIdAndStatus(final String accountId, BoletoStatus status) {
        if (status == null) {
            return boletoMapper.mapFrom(boletoGateway.findAllAccountId(accountId));
        }
        return boletoMapper.mapFrom(boletoGateway.findAllAccountIdAndStatus(accountId, status));
    }
}
