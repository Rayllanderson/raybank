package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.card.transactions.payment.CardCreditPaymentTransaction;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.installment.models.InstallmentPlan;
import com.rayllanderson.raybank.installment.repository.InstallmentPlanGateway;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.transaction.gateway.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInvoiceHelper.createOpenInvoice;
import static com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInvoiceHelper.generateInvoicesFromInstallments;
import static com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInvoiceHelper.getSimulatedPreviousClosingDate;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;

@Service
@RequiredArgsConstructor
public class ProcessInstallmentInInvoiceService {

    private final InvoiceGateway invoiceGateway;
    private final TransactionGateway transactionGateway;
    private final InstallmentPlanGateway planGateway;

    @Transactional
    public List<Invoice> processInvoice(final ProcessInstallmentInvoiceInput input) {
        final var transaction = (CardCreditPaymentTransaction) transactionGateway.findById(input.getTransactionId());

        validateFutureDate(transaction.getMoment());
        final String cardId = transaction.getDebit().getId();
        final Set<Invoice> invoices = new HashSet<>(invoiceGateway.findAllByCardIdAndStatus(cardId, List.of(InvoiceStatus.OPEN, InvoiceStatus.NONE)));
        final Integer dayOfDueDate = getDayOfDueDate(cardId);
        final InvoiceListHelper invoiceList = new InvoiceListHelper(dayOfDueDate, cardId, invoices);

        final var installmentPlan = planGateway.findById(transaction.getPlanId());
        installmentPlan.attachOriginalInvoice(getCurrentInvoice(invoiceList));

        generateInvoicesFromInstallments(invoiceList, installmentPlan.getInstallments().size(), invoiceGateway);

        processInvoice(invoiceList, installmentPlan);
        installmentPlan.checkIfInstallmentsHasInvoice();

        invoiceGateway.saveAll(invoiceList.getInvoices());

        return Collections.unmodifiableList(invoiceList.getSortedInvoices());
    }

    private Invoice getCurrentInvoice(final InvoiceListHelper invoiceList) {
        return invoiceList.getCurrentOpenInvoice().orElseGet(() -> {
            final var newInvoice = createOpenInvoice(invoiceList.getDayOfDueDate(), invoiceList.getCardId());
            invoiceList.add(newInvoice);
            return this.invoiceGateway.save(newInvoice);
        });
    }

    private static void validateFutureDate(final LocalDateTime ocurredOn) {
        if (ocurredOn.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }
    }

    private static void processInvoice(final InvoiceListHelper invoiceList, final InstallmentPlan installmentPlan) {
        invoiceList.getInvoices().forEach(invoice -> {
            for (final Installment installment : installmentPlan.getInstallments()) {
                final var previousInvoiceClosingDate = invoiceList.getPreviousOf(invoice)
                        .map(Invoice::getClosingDate)
                        .orElse(getSimulatedPreviousClosingDate(invoice.getOriginalDueDate(), invoiceList.getDayOfDueDate()));

                final boolean isOnRange = isAfterOrEquals(installment.getDueDate(), previousInvoiceClosingDate) &&
                        installment.getDueDate().isBefore(invoice.getClosingDate());
                if (isOnRange) {
                    invoice.addInstallment(installment);
                    installment.addInvoice(invoice);
                }
            }
        });
    }

    private Integer getDayOfDueDate(final String cardId) {
        return invoiceGateway.getDayOfDueDateByCardId(cardId);
    }
}
