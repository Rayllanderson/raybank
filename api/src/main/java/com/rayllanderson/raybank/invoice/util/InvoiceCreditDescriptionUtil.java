package com.rayllanderson.raybank.invoice.util;

import com.rayllanderson.raybank.invoice.constants.InvoiceCreditDescriptionConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceCreditDescriptionUtil {

    public static String fromRefund(final String transactionDescription) {
        return String.format("%s %s", InvoiceCreditDescriptionConstant.INVOICE_REFUND_DESCRIPTION, transactionDescription);
    }
}
