package com.rayllanderson.raybank.pix.service.limit.find;

import com.rayllanderson.raybank.pix.gateway.PixGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindPixLimitService {

    private final PixGateway pixGateway;

    public FindPixLimitOutput findByAccountId(final String accountId) {
        final var pixLimit = pixGateway.findLimitByAccountId(accountId);

        return new FindPixLimitOutput(pixLimit.getLimit());
    }
}
