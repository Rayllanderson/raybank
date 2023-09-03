package com.rayllanderson.raybank.utils;

import com.rayllanderson.raybank.models.CreditCard;

public class CreditCardCreator {

    public static CreditCard createCreditCardSaved(){
        return CreditCard.builder()
                .id("4b6ecfef-7516-4d48-a6b2-e4be12b37192")
                .number(9999999999999999L)
                .build();
    }
}
