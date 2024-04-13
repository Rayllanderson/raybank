package com.rayllanderson.raybank.boleto.services.refund;

import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.credit.CreditAccountFacadeInput;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoletoRefundService {

    private final BoletoGateway boletoGateway;
    private final CreditAccountFacade creditAccountFacade;
    private final TransactionGateway transactionGateway;

    public void refundUnprocessed() {
        final List<Boleto> boletosUnprocessed = boletoGateway.findAllByStatus(BoletoStatus.PROCESSING_FAILURE);

        boletosUnprocessed.forEach(boleto -> {
            final var originalTransaction = transactionGateway.findByCreditId(boleto.getBarCode());

            final CreditAccountFacadeInput credit = CreditAccountFacadeInput.refundBoleto(boleto, originalTransaction.getId());
            creditAccountFacade.process(credit);

            boleto.refund();
            boletoGateway.save(boleto);
        });
    }
}
