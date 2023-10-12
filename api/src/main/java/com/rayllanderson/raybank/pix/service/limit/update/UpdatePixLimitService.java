package com.rayllanderson.raybank.pix.service.limit.update;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.PixLimit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UpdatePixLimitService {

    private final PixGateway pixGateway;

    public void update(final BigDecimal newLimit, final String accountId) {
        final var pixLimit = pixGateway.findLimitByAccountId(accountId);

        pixLimit.updateLimit(newLimit);

        pixGateway.save(pixLimit);
    }
}
