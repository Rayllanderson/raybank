package com.rayllanderson.raybank.boleto.services.credit;

import com.rayllanderson.raybank.boleto.factory.BeneficiaryFactory;
import com.rayllanderson.raybank.boleto.gateway.BoletoGateway;
import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.boleto.models.BoletoPaymentAttempt;
import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import com.rayllanderson.raybank.boleto.repositories.BoletoPaymentAttemptRepository;
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
    private final BoletoPaymentAttemptRepository paymentAttemptRepository;

    public void credit() {
        final List<Boleto> processingBoletos = boletoGateway.findAllByStatus(BoletoStatus.PROCESSING);

        processingBoletos.forEach(boleto -> {
            try {
                final Transaction originalTransaction = transactionGateway.findByCreditId(boleto.getBarCode());

                final var credit = inputMapper.from(boleto, originalTransaction);
                beneficiaryFactory.receiveCredit(credit);

                boleto.concludePayment();
                boletoGateway.save(boleto);

                deleteAttemptsIfExists(boleto);
            } catch (final Exception e) {
                log.error("Error to conclude payment for boleto {}", boleto.getBarCode(), e);
                handlerError(boleto);
            }
        });
    }

    private void deleteAttemptsIfExists(Boleto boleto) {
        paymentAttemptRepository.findByBoletoBarCode(boleto.getBarCode())
                .ifPresent(paymentAttempt -> paymentAttemptRepository.deleteById(paymentAttempt.getId()));
    }

    private void handlerError(final Boleto boleto) {
        final var boletoPaymentAttempt = paymentAttemptRepository.findByBoletoBarCode(boleto.getBarCode())
                .orElse(new BoletoPaymentAttempt(boleto));

        boletoPaymentAttempt.increase();

        paymentAttemptRepository.save(boletoPaymentAttempt);

        if (boletoPaymentAttempt.maxAttemptsExceeded()) {
            boleto.unprocessed();
            boletoGateway.save(boleto);
            paymentAttemptRepository.deleteById(boletoPaymentAttempt.getId());
        }
    }
}
