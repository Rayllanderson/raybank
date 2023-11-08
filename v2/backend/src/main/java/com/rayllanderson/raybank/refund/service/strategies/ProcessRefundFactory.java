package com.rayllanderson.raybank.refund.service.strategies;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessRefundFactory {

    private final List<RefundService> refundPayments;

    public RefundService getRefundServiceBy(final RefundCommand command) {
        return refundPayments.stream()
                .filter(r -> r.supports(command))
                .findFirst()
                .orElseThrow(RefundStrategyNotFoundException::new);
    }
}
