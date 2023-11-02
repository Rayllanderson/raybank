package com.rayllanderson.raybank.card.services.refund.credit;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.installment.services.refund.PartialRefundInsallmentInput;
import com.rayllanderson.raybank.installment.services.refund.PartialRefundInsallmentService;
import com.rayllanderson.raybank.refund.service.RefundOutput;
import com.rayllanderson.raybank.refund.service.strategies.RefundCommand;
import com.rayllanderson.raybank.refund.service.strategies.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PartialRefundCardCreditService implements RefundService {

    private final List<RefundCardProcess> refundProcesses;
    private final PartialRefundInsallmentService partialRefundInsallmentService;

    @Override
    @Transactional
    public RefundOutput process(final RefundCommand command) {
        final var transaction = (CardCreditPaymentTransaction) command.getTransaction();

        final BigDecimal amountToBeRefund = command.getAmount();
        partialRefundInsallmentService.process(new PartialRefundInsallmentInput(transaction.getPlanId(), amountToBeRefund));

        refundProcesses.forEach(service -> service.refund(transaction, amountToBeRefund));

        return new RefundOutput(transaction.getId(), amountToBeRefund);
    }

    @Override
    public boolean supports(final RefundCommand command) {
        final boolean isCardCredidTransaction = command.getTransaction() instanceof CardCreditPaymentTransaction;
        final boolean isPartialRefund = command.getAmount().compareTo(command.getTransaction().getAmount()) != 0;

        return isCardCredidTransaction && isPartialRefund;
    }
}
