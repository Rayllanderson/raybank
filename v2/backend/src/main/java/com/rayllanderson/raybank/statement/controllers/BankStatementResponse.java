package com.rayllanderson.raybank.statement.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BankStatementResponse {
    private String id;
    private LocalDateTime moment;
    private BigDecimal amount;
    private String type;
    private String method;
    private String financialMovement;
    private String debitName;
    private String creditName;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer installments;
}
