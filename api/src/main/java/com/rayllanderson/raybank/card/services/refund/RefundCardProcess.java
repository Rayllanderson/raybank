package com.rayllanderson.raybank.card.services.refund;

import com.rayllanderson.raybank.transaction.models.Transaction;

import java.math.BigDecimal;

interface RefundCardProcess {

    void refund(Transaction transaction, BigDecimal amount);
}
