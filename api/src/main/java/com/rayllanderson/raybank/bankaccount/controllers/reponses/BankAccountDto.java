package com.rayllanderson.raybank.bankaccount.controllers.reponses;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {
    private String id;
    private String userName;
    private Integer accountNumber;
    private BigDecimal balance;
    private String cardId;
//    private CreditCardDto creditCardDto; //todo:: criar novo dto para cartao

    public static BankAccountDto fromBankAccount(BankAccount bankAccount){
        BankAccountDto dto = new ModelMapper().map(bankAccount, BankAccountDto.class);
        dto.setUserName(bankAccount.getUser().getName());
        dto.setCardId(Optional.ofNullable(bankAccount.getCard()).map(Card::getId).orElse(null));
//        dto.setCreditCardDto(CreditCardDto.fromCreditCard(bankAccount.getCard()));
        return dto;
    }
}
