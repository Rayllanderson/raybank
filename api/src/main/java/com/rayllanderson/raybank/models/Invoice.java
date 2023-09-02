package com.rayllanderson.raybank.models;

import com.rayllanderson.raybank.utils.DateManagerUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Invoice implements Comparable<Invoice> {
    @Id
    private String id;
    private LocalDate dueDate;
    private LocalDate closingDate;
    private BigDecimal total;
    private InvoiceStatus status;
    @OneToMany(cascade = CascadeType.MERGE)
    private List<Installment> installments = new ArrayList<>();

    @Transient
    private static final int DAYS_BEFORE_CLOSE = 6;

    protected void processPayment(String description, BigDecimal total, BigDecimal installmentValue, LocalDateTime date) {
        this.total = total.add(installmentValue);
        final Installment installment = Installment.create(description, total, installmentValue, date);
        this.installments.add(installment);
    }

    protected static Invoice create(LocalDate dueDate) {
        final LocalDate nextWorkingDay = DateManagerUtil.getNextWorkingDayOf(dueDate);
        return new Invoice(UUID.randomUUID().toString(),
                nextWorkingDay,
                nextWorkingDay.minusDays(DAYS_BEFORE_CLOSE),
                BigDecimal.ZERO,
                InvoiceStatus.NONE,
                new ArrayList<>());
    }

    @Override
    public int compareTo(@NotNull Invoice o) {
        if (this.dueDate.isBefore(o.getDueDate())) return -1;
        if (this.dueDate.isAfter(o.dueDate)) return 1;
        else return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        return getId() != null ? getId().equals(invoice.getId()) : invoice.getId() == null;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
