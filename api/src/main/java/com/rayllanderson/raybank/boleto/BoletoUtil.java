package com.rayllanderson.raybank.boleto;

import com.rayllanderson.raybank.utils.DateManagerUtil;
import com.rayllanderson.raybank.utils.RandomUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BoletoUtil {

    public static String generateBarCode(BigDecimal boletoValue) {
        final var randomNumbers = String.valueOf(RandomUtils.generate(15)) + RandomUtils.generate(18);
        return String.format("%s%s%010d",BoletoConstant.PAYMENT_INSTITUTION_CODE, randomNumbers, boletoValue.intValue());
    }

    public static LocalDate generateExpirationDate(final LocalDate creationDate) {
        return DateManagerUtil.getNextWorkingDayOf(creationDate.plusDays(3));
    }
}
