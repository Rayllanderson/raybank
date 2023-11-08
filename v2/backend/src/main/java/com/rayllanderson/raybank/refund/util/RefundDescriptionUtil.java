package com.rayllanderson.raybank.refund.util;

import com.rayllanderson.raybank.shared.constants.DescriptionConstant;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RefundDescriptionUtil {

    public static String fromOriginalTransaction(final String transactionDescription) {
        return String.format("%s %s", DescriptionConstant.REFUND_DESCRIPTION, transactionDescription);
    }
}
