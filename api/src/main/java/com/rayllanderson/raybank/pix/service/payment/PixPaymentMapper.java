package com.rayllanderson.raybank.pix.service.payment;

import com.rayllanderson.raybank.pix.controllers.payment.PixPaymentResponse;
import com.rayllanderson.raybank.pix.model.Pix;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PixPaymentMapper {


    @Mapping(target = "e2eId", source = "pix.id")
    PixPaymentOutput from(Pix pix, String transactionId);

    PixPaymentResponse from(PixPaymentOutput pix);
}
