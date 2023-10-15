package com.rayllanderson.raybank.pix.service.limit;

import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckLimitService {

    private final PixGateway pixGateway;

    public void checkLimit(final PixKey debitKey, final BigDecimal transactionAmount) {
        final var limit = pixGateway.findLimitByAccountId(debitKey.getAccountId());
        if (!limit.hasLimitFor(transactionAmount)) {
            throw UnprocessableEntityException.withFormatted("Limite insuficiente para transação. Seu limite disponível é de %s", limit.getLimit());
        }
    }
}
