package com.rayllanderson.raybank.pix.util;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PixQrCodeGenerator {

    private static final EMV PAYLOAD_FORMAT_INDICATOR = new EMV("00", "02");
    private static final EMV MERCHANT_ACCOUNT_INFORMATION_CARD = new EMV("04", "14");
    private static final EMV MERCHANT_ACCOUNT_INFORMATION = new EMV("26", "58", "BR.GOV.BCB.PIX");
    private static final EMV MERCHANT_CATEGORY_CODE = new EMV("52", "04", "0000");
    private static final EMV TRANSACTION_CURRENCY = new EMV("53", "03", "986");
    private static final EMV TRANSACTION_AMOUNT = new EMV("54", "06");
    private static final EMV COUNTRY_CODE = new EMV("58", "02", "BR");
    private static final EMV MERCHANT_NAME = new EMV("59", "17");
    private static final EMV MERCHANT_CITY = new EMV("60", "08", "SAO PAULO");
    private static final EMV POSTAL_CODE = new EMV("61", "08", "05031***");
    private static final EMV CRC16_CCITT = new EMV("63", "04");

    public static String generateQrCode(PixKey credit, BigDecimal amount) {
        return payloadFormatIndicator() +
                MERCHANT_ACCOUNT_INFORMATION_CARD.getFormated() +
                merchantAccountInformation(credit) +
                merchantCategoryCode() +
                transactionCurrency() +
                transactionAmount(amount) +
                countryCode() +
                merchantName(credit.getName()) +
                merchantCity() +
                postalCode() +
                crc16Ccit();
    }

    private static StringBuilder payloadFormatIndicator() {
        return new StringBuilder().append(PAYLOAD_FORMAT_INDICATOR.getFormated());
    }

    private static StringBuilder merchantAccountInformation(final PixKey creditPixKey) {
        return new StringBuilder()
                .append(MERCHANT_ACCOUNT_INFORMATION.getFormated())
                .append(String.format("%s%s", MERCHANT_ACCOUNT_INFORMATION.defaultValue, creditPixKey.getKey()));
    }

    private static StringBuilder merchantCategoryCode() {
        return new StringBuilder()
                .append(MERCHANT_CATEGORY_CODE.getFormated())
                .append(MERCHANT_CATEGORY_CODE.defaultValue);
    }

    private static StringBuilder transactionCurrency() {
        return new StringBuilder()
                .append(TRANSACTION_CURRENCY.getFormated())
                .append(TRANSACTION_CURRENCY.defaultValue);
    }

    private static StringBuilder transactionAmount(BigDecimal amount) {
        return new StringBuilder()
                .append(TRANSACTION_AMOUNT.getFormated())
                .append(amount);
    }

    private static StringBuilder countryCode() {
        return new StringBuilder()
                .append(COUNTRY_CODE.getFormated())
                .append(COUNTRY_CODE.defaultValue);
    }

    private static StringBuilder merchantName(String creditName) {
        return new StringBuilder()
                .append(MERCHANT_NAME.getFormated())
                .append(creditName);
    }

    private static StringBuilder merchantCity() {
        return new StringBuilder()
                .append(MERCHANT_CITY.getFormated())
                .append(MERCHANT_CITY.defaultValue);
    }

    private static StringBuilder postalCode() {
        return new StringBuilder()
                .append(POSTAL_CODE.getFormated())
                .append(POSTAL_CODE.defaultValue);
    }

    private static StringBuilder crc16Ccit() {
        return new StringBuilder()
                .append(CRC16_CCITT.getFormated())
                .append(UUID.randomUUID().toString().split("-")[0].toUpperCase());
    }

    @AllArgsConstructor
    private static class EMV {
        String id;
        String size;
        String defaultValue;

        EMV(String id, String size) {
            this.id = id;
            this.size = size;
        }

        String getFormated() {
            return String.format("%s%s", id, size);
        }
    }
}
