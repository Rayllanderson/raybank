package com.rayllanderson.raybank.boleto.controllers.find;

import com.rayllanderson.raybank.boleto.models.BoletoStatus;
import lombok.Getter;

@Getter
public enum BoletoStatusParamRequest {
    WAITING_PAYMENT(BoletoStatus.WAITING_PAYMENT), EXPIRED(BoletoStatus.EXPIRED), PAID(BoletoStatus.PAID), PROCESSING(BoletoStatus.PROCESSING), ALL(null);

    private final BoletoStatus boletoStatus;

    BoletoStatusParamRequest(BoletoStatus boletoStatus) {
        this.boletoStatus = boletoStatus;
    }

    public static BoletoStatusParamRequest from(String status) {
        try {
            return BoletoStatusParamRequest.valueOf(status.toUpperCase());
        } catch (Exception e) {
            return BoletoStatusParamRequest.ALL;
        }
    }
}
