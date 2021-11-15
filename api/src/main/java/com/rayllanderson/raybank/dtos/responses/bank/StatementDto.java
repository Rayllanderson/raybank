package com.rayllanderson.raybank.dtos.responses.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.dtos.responses.bank.enums.IdentificationType;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.enums.StatementType;
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
public class StatementDto {
    private Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String to;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private Instant moment;
    private StatementType statementType;
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private IdentificationType identificationType;

    public static StatementDto fromStatement(BankStatement statement) {
        StatementDto dto = new ModelMapper().map(statement, StatementDto.class);
        BankAccount senderAccount = statement.getAccountSender();
        String identificationName = null;
        if (senderAccount != null)
            identificationName = senderAccount.getUser().getName();
        boolean isAmountPositive = statement.getAmount().compareTo(BigDecimal.ZERO) > 0;
        boolean isTransfer = statement.getStatementType().equals(StatementType.TRANSFER);
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
