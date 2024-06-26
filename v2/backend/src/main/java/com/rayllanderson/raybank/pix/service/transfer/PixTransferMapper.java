package com.rayllanderson.raybank.pix.service.transfer;

import com.rayllanderson.raybank.pix.controllers.transfer.PixTransferResponse;
import com.rayllanderson.raybank.pix.model.Pix;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PixTransferMapper {


    @Mapping(target = "e2eId", source = "pix.id")
    PixTransferOutput from(Pix pix, String transactionId);

    @Mapping(target = "transactionType", constant = "PIX")
    PixTransferResponse from(PixTransferOutput pix);
}
