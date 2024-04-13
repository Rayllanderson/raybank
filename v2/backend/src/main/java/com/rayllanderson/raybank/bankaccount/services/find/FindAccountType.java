package com.rayllanderson.raybank.bankaccount.services.find;

public enum FindAccountType {
    PIX, CONTACT, ACCOUNT;

    public static FindAccountType from(String type) {
        try {
            return FindAccountType.valueOf(type.toUpperCase());
        } catch (Exception e) {
            return FindAccountType.ACCOUNT;
        }
    }
}
