package com.rayllanderson.raybank.pix.service.pixreturn;

import com.rayllanderson.raybank.pix.controllers.pixreturn.PixReturnRequest;
import com.rayllanderson.raybank.pix.controllers.pixreturn.PixReturnResponse;
import com.rayllanderson.raybank.pix.model.Pix;
import com.rayllanderson.raybank.pix.model.PixReturn;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PixReturnMapper {

    @Mapping(target = "e2eId", source = "pixReturn.id")
    @Mapping(target = "originalE2eId", source = "pix.id")
    @Mapping(target = "debit", expression = "java(new PixReturnOutput.Debit(pix.getCreditName()))")
    @Mapping(target = "credit", expression = "java(new PixReturnOutput.Credit(pix.getDebitName()))")
    @Mapping(target = "occuredOn", source = "pixReturn.occuredOn")
    @Mapping(target = "amount", source = "pixReturn.amount")
    @Mapping(target = "message", source = "pixReturn.message")
    PixReturnOutput from(Pix pix, PixReturn pixReturn, String transactionId);

    PixReturnInput from(PixReturnRequest request, String returningAccountId);

    PixReturnResponse from(PixReturnOutput pixReturnOutput);
}
