package com.rayllanderson.raybank.dtos.responses.bank;

import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.enums.StatementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementDto {
    private Long id;
    private LocalDateTime moment;
    private StatementType statementType;
    private BigDecimal amount;
    private String accountSenderName;

    public static StatementDto fromStatement(BankStatement statement){
        StatementDto dto = new ModelMapper().map(statement, StatementDto.class);
        dto.setAccountSenderName(statement.getAccountSender().getUser().getName());
        return dto;
    }
}
