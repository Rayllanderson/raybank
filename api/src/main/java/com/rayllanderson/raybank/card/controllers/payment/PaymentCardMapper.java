package com.rayllanderson.raybank.card.controllers.payment;

import com.rayllanderson.raybank.card.services.payment.PaymentCardInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = PaymentCardInput.PaymentType.class)
public interface PaymentCardMapper {

    @Mapping(target = "paymentType", expression = "java(PaymentCardInput.PaymentType.from(request.getPaymentType()))")
    PaymentCardInput from(PaymentCardRequest request, String establishmentId);
}
