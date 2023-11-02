package com.rayllanderson.raybank.card.services.refund.credit;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.installment.services.refund.FullRefundInsallmentService;
import com.rayllanderson.raybank.refund.service.RefundOutput;
import com.rayllanderson.raybank.refund.service.strategies.RefundService;
import com.rayllanderson.raybank.refund.service.strategies.RefundCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FullRefundCardCreditService implements RefundService {

    private final List<RefundCardProcess> refundProcesses;
    private final FullRefundInsallmentService fullRefundInsallmentService;

    @Override
    @Transactional
    public RefundOutput process(final RefundCommand command) {
        final var transaction = (CardCreditPaymentTransaction) command.getTransaction();

        final var fullRefundInsallment = fullRefundInsallmentService.process(transaction.getPlanId());

        refundProcesses.forEach(service -> {
            if (service instanceof DebitEstablishmentRefundProcess) {
                service.refund(transaction, command.getAmount());
                return;
            }
            service.refund(transaction, fullRefundInsallment.getAmountRefunded());
        });

        return new RefundOutput(transaction.getId(), fullRefundInsallment.getAmountRefunded());
    }

    @Override
    public boolean supports(final RefundCommand command) {
        final boolean isCardCredidTransaction = command.getTransaction() instanceof CardCreditPaymentTransaction;
        final boolean isFullRefund = command.getAmount().compareTo(command.getTransaction().getAmount()) == 0;

        return isCardCredidTransaction && isFullRefund;
    }
}
