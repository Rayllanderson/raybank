package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.CreditCard;

public class CreditCardCreator {

    public static CreditCard createCreditCardSaved(){
        return CreditCard.builder()
                .id(1L)
                .cardNumber(9999999999999999L)
                .build();
    }
}
