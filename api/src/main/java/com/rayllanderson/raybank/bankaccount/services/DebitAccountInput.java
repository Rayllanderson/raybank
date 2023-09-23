package com.rayllanderson.raybank.bankaccount.services;

import com.rayllanderson.raybank.bankaccount.model.DebitOriginType;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DebitAccountInput {

    private String accountId;
    private BigDecimal amount;
    private DebitOrigin origin;

    @Getter
    @Setter
    public static class DebitOrigin {
        private String identifier;
        private DebitOriginType type;
    }
}
