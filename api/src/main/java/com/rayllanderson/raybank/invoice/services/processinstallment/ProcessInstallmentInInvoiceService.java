package com.rayllanderson.raybank.invoice.services.processinstallment;

import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanMapper;
import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanOutput;
import com.rayllanderson.raybank.installment.services.CreateInstallmentPlanService;
import com.rayllanderson.raybank.invoice.gateway.InvoiceGateway;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInvoiceHelper.createOpenInvoice;
import static com.rayllanderson.raybank.invoice.services.processinstallment.ProcessInvoiceHelper.generateInvoicesFromInstallments;
import static com.rayllanderson.raybank.utils.DateManagerUtil.isAfterOrEquals;

@Service
@RequiredArgsConstructor
public class ProcessInstallmentInInvoiceService {

    private final InvoiceGateway invoiceGateway;
    private final CreateInstallmentPlanService createInstallmentPlanService;
    private final CreateInstallmentPlanMapper planMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<Invoice> processInvoice(final ProcessInstallmentInvoiceInput processInstallmentInvoiceInput) {
        validateFutureDate(processInstallmentInvoiceInput);
        final String cardId = processInstallmentInvoiceInput.getCardId();
        final Set<Invoice> invoices = new HashSet<>(invoiceGateway.findAllByCardIdAndStatus(cardId, List.of(InvoiceStatus.OPEN, InvoiceStatus.NONE)));
        final Integer dayOfDueDate = getDayOfDueDate(cardId);
        final InvoiceListHelper invoiceList = new InvoiceListHelper(dayOfDueDate, cardId, invoices);

        final Invoice currentInvoice = getCurrentInvoice(invoiceList);

        final var createPlanInput = planMapper.from(processInstallmentInvoiceInput, currentInvoice.getId());
        final var installmentPlan = createInstallmentPlanService.create(createPlanInput);

        generateInvoicesFromInstallments(invoiceList, installmentPlan.getInstallments().size());

        processInvoice(invoiceList, installmentPlan);

        return Collections.unmodifiableList(invoiceList.getSortedInvoices());
    }

    private static Invoice getCurrentInvoice(final InvoiceListHelper invoiceList) {
        return invoiceList.getCurrentOpenInvoice().orElseGet(() -> {
            final var newInvoice = createOpenInvoice(invoiceList.getDayOfDueDate(), invoiceList.getCardId());
            invoiceList.add(newInvoice);
            return newInvoice;
        });
    }

    private static void validateFutureDate(final ProcessInstallmentInvoiceInput processInstallmentInvoiceInput) {
        if (processInstallmentInvoiceInput.getOcurredOn().isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("'ocurredOn' must not be in the future");
        }
    }

    private static void processInvoice(final InvoiceListHelper invoiceList, final CreateInstallmentPlanOutput installmentPlan) {
        invoiceList.getInvoices().forEach(invoice -> {
            for (CreateInstallmentPlanOutput.InstallmentOutput installment : installmentPlan.getInstallments()) {
                var previousInvoiceClosingDate = invoiceList.getPreviousOf(invoice)
                        .map(Invoice::getClosingDate)
                        .orElse(invoice.getClosingDate().minusMonths(1));

                final boolean isOnRange = installment.getDueDate().isAfter(previousInvoiceClosingDate) &&
                        isAfterOrEquals(invoice.getClosingDate(), installment.getDueDate());
                if (isOnRange) {
                    invoice.processInstallment(installmentPlan.getInstallmentValue(), installment.getId());
                    break;
                }
            }
        });
    }

    private Integer getDayOfDueDate(final String cardId) {
        return invoiceGateway.getDayOfDueDateByCardId(cardId);
    }
}
