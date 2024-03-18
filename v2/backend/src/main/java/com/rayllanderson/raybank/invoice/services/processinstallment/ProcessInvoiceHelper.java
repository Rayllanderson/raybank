package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.utils.DateManagerUtil;
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
            Invoice newInvoice = createNewInvoice(invoiceList, dueDate);
            invoiceList.add(newInvoice);
            dueDate = newInvoice.getDueDate();
        }
    }

    public static LocalDate getSimulatedPreviousClosingDate(LocalDate dueDate, int dayOfDueDate) {
        return DateManagerUtil.minusOneMonthKeepingCurrentDayOfMonth(dueDate, dayOfDueDate).minusDays(Invoice.DAYS_BEFORE_CLOSE);
    }

    public static void generateInvoicesFromInstallments(final InvoiceListHelper invoiceList, final int installmentsSize, InvoiceGateway invoiceGateway) {
        LocalDate dueDate = invoiceList.getLastInvoiceDueDate();

        final int difference = installmentsSize - invoiceList.getInvoices().size();
        for (int i = 0; i < difference; i++) {
            Invoice newInvoice = createNewInvoice(invoiceList, dueDate);
            invoiceList.add(newInvoice);
            invoiceGateway.save(newInvoice);
            dueDate = newInvoice.getDueDate();
        }
    }

    private static Invoice createNewInvoice(InvoiceListHelper invoiceList, LocalDate dueDate) {
        Invoice newInvoice = Invoice.create(plusOneMonthKeepingCurrentDayOfMonth(dueDate, invoiceList.getDayOfDueDate()), invoiceList.getCardId());
        if (newInvoice.getClosingDate().isEqual(LocalDate.now())) {
            newInvoice.changeClosingDate();
        }
        return newInvoice;
    }
    public static Invoice createOpenInvoice(final int dayOfDueDate, final String cardId) {
        return Invoice.createOpenInvoice(plusOneMonthOf(dayOfDueDate), cardId);
    }
}
