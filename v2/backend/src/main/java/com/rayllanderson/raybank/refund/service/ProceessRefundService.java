package com.rayllanderson.raybank.refund.service;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.refund.service.strategies.ProcessRefundFactory;
import com.rayllanderson.raybank.refund.service.strategies.RefundCommand;
import com.rayllanderson.raybank.refund.service.strategies.RefundService;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.REFUND_AMOUNT_INVALID;

@Service
@RequiredArgsConstructor
public class ProceessRefundService {

    private final TransactionGateway transactionGateway;
    private final ProcessRefundFactory refundFactory;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public RefundOutput process(final ProcessRefundInput refund) {
        final var transaction = transactionGateway.findById(refund.getTransactionId());
        validate(refund, transaction);

        final var refundCommand = new RefundCommand(transaction, refund.getAmount(), refund.getReason());

        final RefundService refundService = refundFactory.getRefundServiceBy(refundCommand);
        return refundService.process(refundCommand);
    }

    private void validate(final ProcessRefundInput refund, final Transaction transaction) {
        if (refund.getAmount().compareTo(transaction.getAmount()) > 0) {
            throw UnprocessableEntityException.with(REFUND_AMOUNT_INVALID, "Valor do reembolso maior do que o da transação");
        }
    }
}
