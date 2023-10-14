package com.rayllanderson.raybank.bankaccount.facades.debit;

import com.rayllanderson.raybank.bankaccount.services.debit.DebitAccountInput;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DebitAccountMapper {
    DebitAccountInput from(DebitAccountFacadeInput facadeInput);
}
