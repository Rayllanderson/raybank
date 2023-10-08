package com.rayllanderson.raybank.boleto.services.credit;

import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BoletoCreditService {

    private final BoletoGateway boletoGateway;
    private final BeneficiaryFactory beneficiaryFactory;
    private final BoletoCreditMapper inputMapper;
    private final TransactionGateway transactionGateway;

    public void credit() {
        final List<Boleto> paidBoletos = boletoGateway.findAllByStatus(BoletoStatus.PROCESSING);

        paidBoletos.forEach(boleto -> {
            try {
                final Transaction originalTransaction = transactionGateway.findByCreditId(boleto.getBarCode());

                final var credit = inputMapper.from(boleto, originalTransaction);
                beneficiaryFactory.receiveCredit(credit);

                boleto.concludePayment();
                boletoGateway.save(boleto);
            } catch (final Exception e) {
                log.error("Error to conclude payment for boleto {}", boleto.getBarCode(), e);
            }
        });
    }
}
