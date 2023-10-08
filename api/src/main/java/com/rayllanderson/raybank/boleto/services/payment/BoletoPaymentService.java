package com.rayllanderson.raybank.boleto.services.payment;

import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacade;
import com.rayllanderson.raybank.bankaccount.facades.DebitAccountFacadeInput;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoletoPaymentService {

    private final BoletoGateway boletoGateway;
    private final DebitAccountFacade debitAccountFacade;

    @Transactional
    public Transaction pay(final BoletoPaymentInput input) {
        final Boleto boleto = boletoGateway.findByBarCode(input.getBarCode());

        boleto.validateIfCanReceivePayment();

        final var transaction = debitAccountFacade.process(DebitAccountFacadeInput.from(input.getPayerId(), boleto));

        boleto.liquidate(input.getPayerId());
        boletoGateway.save(boleto);

        return transaction;
    }
}
