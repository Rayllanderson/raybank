package com.rayllanderson.raybank.pix.service.pixreturn;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PixReturnInput {
    private String pixId;
    private String returningAccountId;
    private BigDecimal amount;
    private String message;
}
