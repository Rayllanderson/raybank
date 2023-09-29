package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthKeepingCurrentDayOfMonth;
import static com.rayllanderson.raybank.utils.DateManagerUtil.plusOneMonthOf;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessInvoiceHelper {

    public static void generateInvoicesFromInstallments(final InvoiceListHelper invoiceList, final int installmentsSize) {
        LocalDate dueDate = invoiceList.getLastInvoiceDueDate();

        final int difference = installmentsSize - invoiceList.getInvoices().size();
        for (int i = 0; i < difference; i++) {
            Invoice newInvoice = Invoice.create(plusOneMonthKeepingCurrentDayOfMonth(dueDate, invoiceList.getDayOfDueDate()), invoiceList.getCardId());
            invoiceList.add(newInvoice);
            dueDate = newInvoice.getDueDate();
        }
    }

    public static Invoice createOpenInvoice(final int dayOfDueDate, final String cardId) {
        return Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), cardId);
    }
}
