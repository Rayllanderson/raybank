package com.rayllanderson.raybank.dtos.responses.bank;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rayllanderson.raybank.dtos.responses.bank.enums.IdentificationType;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.transaction.Transaction;
import com.rayllanderson.raybank.models.TransactionType;
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
public class TransactionDto {
    private String id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String from;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String to;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;
    private Instant moment;
    private TransactionType transactionType;
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private IdentificationType identificationType;

    public static TransactionDto fromTransaction(Transaction transaction) {
        TransactionDto dto = new ModelMapper().map(transaction, TransactionDto.class);
        BankAccount senderAccount = transaction.getAccountSender();
        dto.setTransactionType(transaction.getType());
        String identificationName = null;
        if (senderAccount != null)
            identificationName = senderAccount.getUser().getName();
        boolean isAmountPositive = transaction.getAmount().compareTo(BigDecimal.ZERO) > 0;
        boolean isTransfer = transaction.getType().equals(TransactionType.TRANSFER);
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
