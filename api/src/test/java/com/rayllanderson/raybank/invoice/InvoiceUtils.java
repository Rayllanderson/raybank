package com.rayllanderson.raybank.invoice;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.invoice.models.Installment;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.utils.MoneyUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InvoiceUtils {

    public static Invoice create(LocalDate dueDate, BigDecimal total, InvoiceStatus status) {
        return new Invoice(UUID.randomUUID().toString(), dueDate, dueDate.minusDays(6), total, status, new Card(), new ArrayList<>());
    }

    public static Invoice create(LocalDate dueDate, BigDecimal total, InvoiceStatus status, Installment... installments) {
        return new Invoice(UUID.randomUUID().toString(), dueDate, dueDate.minusDays(6), total, status, new Card(), new ArrayList<>(List.of(installments)));
    }

    public static Installment installment(String description, BigDecimal total, BigDecimal value) {
        return new Installment(UUID.randomUUID().toString(), description, total, value, LocalDateTime.now());
    }

    public static BigDecimal bigDecimalOf(Long o) {
        return bigDecimalOf(o.toString());
    }

    public static BigDecimal bigDecimalOf(String o) {
        return MoneyUtils.from(new BigDecimal(o));
    }

    public static BigDecimal bigDecimalOf(double o) {
        return bigDecimalOf(String.valueOf(o));
    }

    public static LocalDate parse(String date) {
        return LocalDate.parse(date);
    }
}
