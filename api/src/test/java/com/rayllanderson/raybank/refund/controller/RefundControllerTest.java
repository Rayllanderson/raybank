package com.rayllanderson.raybank.refund.controller;

import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.RefundRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.helpers.CardHelper;
import com.rayllanderson.raybank.e2e.helpers.InvoiceHelper;
import com.rayllanderson.raybank.e2e.register.RegisterEstablishment;
import com.rayllanderson.raybank.e2e.security.WithEstablishmentUser;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCredit;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.utils.Await.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class RefundControllerTest extends E2eApiTest {

    @Autowired
    private CardCreator cardCreator;
    @Autowired
    private CardHelper cardHelper;
    @Autowired
    private InvoiceHelper invoiceHelper;
    private static final String URL = "/api/v1/external/transactions";

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldFullRefundCardCreditTransaction() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);

        final var request = RefundRequestBuilder.build(cardCreditTransaction.getAmount(), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(0)));

        await(2); // to handler async methods

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(2);
        assertThatInvoicesFromCard(card.getId()).hasSize(1);
        Invoice invoice = getCurrentInvoice(card.getId());
        assertThat(invoice.getTotal()).isZero();
        assertThat(invoice.getInstallments()).hasSize(1).allMatch(Installment::isCanceled);
        assertThat(invoice.getCredits()).hasSize(1).allMatch(InvoiceCredit::isRefund);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldFullRefundCardCreditTransactionWhenAlredyPaidInstallment() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        deposit("10", card.getAccountId());
        await(2); // to handler async methods
        invoiceHelper.payCurrentInvoice(BigDecimal.TEN, card);

        final var request = RefundRequestBuilder.build(cardCreditTransaction.getAmount(), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(10.0)));

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(4);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(4);
        assertThatInvoicesFromCard(card.getId()).hasSize(1);
        Invoice invoice = getCurrentInvoice(card.getId());
        assertThat(invoice.getTotal()).isEqualTo(new BigDecimal("-10.00"));
        assertThat(invoice.getInstallments()).hasSize(1).allMatch(Installment::isRefunded);
        assertThat(invoice.getCredits()).hasSize(2).anyMatch(InvoiceCredit::isRefund).anyMatch(InvoiceCredit::isPayment);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldPartialRefundCardCreditTransaction() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);

        final var request = RefundRequestBuilder.build(new BigDecimal(5), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));

        await(2); // to handler async methods

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(2);
        assertThatInvoicesFromCard(card.getId()).hasSize(1);
        Invoice invoice = getCurrentInvoice(card.getId());
        assertThat(invoice.getTotal()).isEqualTo(new BigDecimal("5.00"));
        assertThat(invoice.getInstallments()).hasSize(1).allMatch(Installment::isOpen);
        assertThat(invoice.getCredits()).hasSize(1).allMatch(InvoiceCredit::isRefund);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldPartialRefundCardCreditTransactionWhenAlredyPaidInstallment() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        deposit("10", card.getAccountId());
        await(2); // to handler async methods
        invoiceHelper.payCurrentInvoice(BigDecimal.TEN, card);

        final var request = RefundRequestBuilder.build(new BigDecimal(5), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(4);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(4);
        assertThatInvoicesFromCard(card.getId()).hasSize(1);
        Invoice invoice = getCurrentInvoice(card.getId());
        assertThat(invoice.getTotal()).isEqualTo(new BigDecimal("-5.00"));
        assertThat(invoice.getInstallments()).hasSize(1).allMatch(Installment::isPaid);
        assertThat(invoice.getCredits()).hasSize(2).anyMatch(InvoiceCredit::isRefund).anyMatch(InvoiceCredit::isPayment);
    }
}