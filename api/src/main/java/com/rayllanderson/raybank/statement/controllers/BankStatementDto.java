package com.rayllanderson.raybank.statement.controllers;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.dtos.responses.bank.enums.IdentificationType;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.models.BankStatementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankStatementDto {
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String to;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private Instant moment;
    private BankStatementType bankStatementType;
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private IdentificationType identificationType;

    public static BankStatementDto fromBankStatement(BankStatement bankStatement) {
        BankStatementDto dto = new ModelMapper().map(bankStatement, BankStatementDto.class);
        BankAccount senderAccount = null; //todo::
        dto.setBankStatementType(bankStatement.getType());
        String identificationName = null;
        if (senderAccount != null)
            identificationName = senderAccount.getUser().getName();
        boolean isAmountPositive = bankStatement.getAmount().compareTo(BigDecimal.ZERO) > 0;
        boolean isTransfer = bankStatement.getType().equals(BankStatementType.TRANSFER);
        if (isTransfer) {
            if(isAmountPositive) {
                dto.setIdentificationType(IdentificationType.RECEIVER);
                dto.setFrom(identificationName);
            } else {
                dto.setIdentificationType(IdentificationType.SENDER);
                dto.setTo(identificationName);
            }
        }
        return dto;
    }
}
