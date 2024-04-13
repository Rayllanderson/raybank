package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessInvoiceHelperTest {

    @Test
    void shouldGenerateInvoicesFromInstallments() {
        final var invoiceList = new InvoiceListHelper(6, new ArrayList<>());

        ProcessInvoiceHelper.generateInvoicesFromInstallments(invoiceList, 6);

        assertThat(invoiceList.getInvoices()).hasSize(6);
        final var allDueDates = invoiceList.getInvoices().stream().map(Invoice::getDueDate).collect(Collectors.toSet());
        assertThat(allDueDates).hasSize(6).doesNotHaveDuplicates();
    }

    @Test
    void shouldGenerateInvoicesWhenListHasInvoices() {
        final var invoice = Invoice.createOpenInvoice(LocalDate.of(2023, 10, 6), "c");
        final var invoiceList = new InvoiceListHelper(6, List.of(invoice));

        ProcessInvoiceHelper.generateInvoicesFromInstallments(invoiceList, 3);

        assertThat(invoiceList.getInvoices()).hasSize(3);
        final var allDueDates = invoiceList.getInvoices().stream().map(Invoice::getDueDate).collect(Collectors.toSet());
        assertThat(allDueDates).hasSize(3).doesNotHaveDuplicates();
        final var sortedInvoices = invoiceList.getSortedInvoices();
        assertThat(sortedInvoices.get(0).getDueDate()).isEqualTo(LocalDate.parse("2023-10-06"));
        assertThat(sortedInvoices.get(1).getDueDate()).isEqualTo(LocalDate.parse("2023-11-06"));
        assertThat(sortedInvoices.get(2).getDueDate()).isEqualTo(LocalDate.parse("2023-12-06"));
    }

    @Test
    void shouldCreateInvoicesWhenListHasInvoicesWithMoreInstallments() {
        final var invoice = Invoice.createOpenInvoice(LocalDate.of(2023, 10, 6), "c");
        final var invoiceList = new InvoiceListHelper(6, List.of(invoice));

        ProcessInvoiceHelper.generateInvoicesFromInstallments(invoiceList, 12);

        assertThat(invoiceList.getInvoices()).hasSize(12);
        final var allDueDates = invoiceList.getInvoices().stream().map(Invoice::getDueDate).collect(Collectors.toSet());
        assertThat(allDueDates).hasSize(12).doesNotHaveDuplicates();
        final var sortedInvoices = invoiceList.getSortedInvoices();
        assertThat(sortedInvoices.get(0).getDueDate()).isEqualTo(LocalDate.parse("2023-10-06"));
        assertThat(sortedInvoices.get(1).getDueDate()).isEqualTo(LocalDate.parse("2023-11-06"));
        assertThat(sortedInvoices.get(2).getDueDate()).isEqualTo(LocalDate.parse("2023-12-06"));
        assertThat(sortedInvoices.get(3).getDueDate()).isEqualTo(LocalDate.parse("2024-01-08"));
        assertThat(sortedInvoices.get(4).getDueDate()).isEqualTo(LocalDate.parse("2024-02-06"));
        assertThat(sortedInvoices.get(5).getDueDate()).isEqualTo(LocalDate.parse("2024-03-06"));
        assertThat(sortedInvoices.get(6).getDueDate()).isEqualTo(LocalDate.parse("2024-04-08"));
        assertThat(sortedInvoices.get(7).getDueDate()).isEqualTo(LocalDate.parse("2024-05-06"));
        assertThat(sortedInvoices.get(8).getDueDate()).isEqualTo(LocalDate.parse("2024-06-06"));
        assertThat(sortedInvoices.get(9).getDueDate()).isEqualTo(LocalDate.parse("2024-07-08"));
        assertThat(sortedInvoices.get(10).getDueDate()).isEqualTo(LocalDate.parse("2024-08-06"));
        assertThat(sortedInvoices.get(11).getDueDate()).isEqualTo(LocalDate.parse("2024-09-06"));
    }
}