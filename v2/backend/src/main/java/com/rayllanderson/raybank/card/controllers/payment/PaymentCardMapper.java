package com.rayllanderson.raybank.card.controllers.payment;

import com.rayllanderson.raybank.card.services.payment.CardPaymentInput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = CardPaymentInput.PaymentType.class)
public interface PaymentCardMapper {

    @Mapping(target = "paymentType", expression = "java(CardPaymentInput.PaymentType.from(request.getPaymentType()))")
    CardPaymentInput from(CardPaymentRequest request, String establishmentId);
}
