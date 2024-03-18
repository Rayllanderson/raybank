package com.rayllanderson.raybank.invoice.factory;

import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class InvoiceCreatorFactory {

    public static Invoice createNewInvoice(final int dueDay, final String cardId) {
        Invoice newInvoice = Invoice.create(plusOneMonthKeepingCurrentDayOfMonth(LocalDate.now(), dueDay), cardId);
        if (newInvoice.getClosingDate().isEqual(LocalDate.now())) {
            newInvoice.changeClosingDate();
        }
        return newInvoice;
    }
}
