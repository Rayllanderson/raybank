package com.rayllanderson.raybank.refund.service.strategies;

import com.rayllanderson.raybank.refund.service.RefundOutput;

public interface RefundService {

    RefundOutput process(final RefundCommand command);
    boolean supports(final RefundCommand transaction);
}
