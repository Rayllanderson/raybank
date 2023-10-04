package com.rayllanderson.raybank.invoice;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import com.rayllanderson.raybank.installment.models.InstallmentStatus;
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
        return new Invoice(UUID.randomUUID().toString(), dueDate, dueDate, dueDate.minusDays(6), status, new Card(), new ArrayList<>(), new ArrayList<>());
    }

    public static Invoice create(LocalDate dueDate, InvoiceStatus status, Installment... installments) {
        return new Invoice(UUID.randomUUID().toString(), dueDate, dueDate, dueDate.minusDays(6), status, new Card(), new ArrayList<>(List.of(installments)), new ArrayList<>());
    }

    @Deprecated
    public static Invoice create(LocalDate dueDate, BigDecimal total, InvoiceStatus status, Installment... installments) {
        return new Invoice(UUID.randomUUID().toString(), dueDate, dueDate, dueDate.minusDays(6), status, new Card(), new ArrayList<>(List.of(installments)), new ArrayList<>());
    }

    public static Installment installment(String description, BigDecimal value, LocalDate dueDate) {
        return new Installment(UUID.randomUUID().toString(), description, value, dueDate, InstallmentStatus.OPEN, new InstallmentPlan());
    }

    public static Installment installment(LocalDate dueDate, BigDecimal value, InstallmentStatus status) {
        return new Installment(UUID.randomUUID().toString(), null, value, dueDate, status, new InstallmentPlan());
    }

    public static Installment installment(final BigDecimal value) {
        return new Installment(UUID.randomUUID().toString(), null, value, null, InstallmentStatus.OPEN, new InstallmentPlan());
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
