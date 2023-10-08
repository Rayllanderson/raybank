package com.rayllanderson.raybank.boleto.services.credit;

import com.rayllanderson.raybank.boleto.models.Boleto;
import com.rayllanderson.raybank.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface BoletoCreditMapper {

    @Mapping(target = "beneficiaryId", source = "boleto.beneficiary.id")
    @Mapping(target = "beneficiaryType", source = "boleto.beneficiary.type")
    @Mapping(target = "amount", source = "transaction.amount")
    @Mapping(target = "originalTransactionId", source = "transaction.id")
    BoletoCreditInput from(Boleto boleto, Transaction transaction);
}
