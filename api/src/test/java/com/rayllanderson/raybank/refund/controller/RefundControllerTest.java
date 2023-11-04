package com.rayllanderson.raybank.refund.controller;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.RefundRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.helpers.CardHelper;
import com.rayllanderson.raybank.e2e.helpers.InvoiceHelper;
import com.rayllanderson.raybank.e2e.register.RegisterEstablishment;
import com.rayllanderson.raybank.e2e.security.WithEstablishmentUser;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.installment.models.Installment;
import com.rayllanderson.raybank.invoice.models.Invoice;
import com.rayllanderson.raybank.invoice.models.InvoiceCredit;
import com.rayllanderson.raybank.invoice.models.InvoiceStatus;
import com.rayllanderson.raybank.transaction.models.Transaction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSTALLMENTPLAN_PARTIAL_REFUNDED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSTALLMENTPLAN_PARTIAL_REFUND_EXCEEDED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSTALLMENTPLAN_REFUNDED;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.REFUND_AMOUNT_HIGHER;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.REFUND_AMOUNT_INVALID;
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
    void shouldFullRefundCardCreditTransactionWithMultiplesInstallments() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, 2, "amazon", card);
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
        assertThatInvoicesFromCard(card.getId()).hasSize(2
        ).allMatch(invoice ->
            invoice.getInstallments().size() == 1 && invoice.getInstallments().get(0).isCanceled()
        ).anyMatch(invoice ->
            invoice.isOpen() && invoice.getCredits().size() == 1 && invoice.getCredits().get(0).isRefund()
        ).anyMatch(invoice ->
            invoice.getStatus().equals(InvoiceStatus.NONE) && invoice.getCredits().isEmpty()
        );
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
    void shouldDo2PartialRefundCardCreditTransaction() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        final var request = RefundRequestBuilder.build(new BigDecimal(5), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));
        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));

        await(2); // to handler async methods

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(3);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(3);
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(3);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(3);
        assertThatInvoicesFromCard(card.getId()).hasSize(1);
        Invoice invoice = getCurrentInvoice(card.getId());
        assertThat(invoice.getTotal()).isZero();
        assertThat(invoice.getInstallments()).hasSize(1).allMatch(Installment::isPaid);
        assertThat(invoice.getCredits()).hasSize(2).allMatch(InvoiceCredit::isRefund);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldPartialRefundCardCreditTransactionWhenAlredyPaidInstallment() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        deposit("10", card.getAccountId());
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

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenAmountToRefundIsHigherThanTransactionUsingCreditTransaction() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        final var request = RefundRequestBuilder.build(new BigDecimal(10000), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(REFUND_AMOUNT_INVALID.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenPlanIsAlreadyFullRefundedAndTryPartialRefund() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), cardCreditTransaction.getAmount());
        final var request = RefundRequestBuilder.build(1, "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSTALLMENTPLAN_REFUNDED.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenPlanIsAlreadyRefundedAndTryFullRefund() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), cardCreditTransaction.getAmount());
        final var request = RefundRequestBuilder.build(10, "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSTALLMENTPLAN_REFUNDED.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenTryFullRefundPlanWithPartialRefunds() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), 5);
        final var request = RefundRequestBuilder.build(BigDecimal.TEN, "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSTALLMENTPLAN_PARTIAL_REFUNDED.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenAmountToRefundIsHigherThanAvailableToRefund() throws Exception {
        Card card = cardCreator.newCard();
        Transaction cardCreditTransaction = cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), 5);
        final var request = RefundRequestBuilder.build(7, "MD606"); // 7 + 5 = 12 - higher than total (10)

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSTALLMENTPLAN_PARTIAL_REFUND_EXCEEDED.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldFullRefundCardDebitTransaction() throws Exception {
        Card card = cardCreator.newCard();
        deposit("10", card.getAccountId());
        Transaction cardCreditTransaction = cardHelper.doDebitPayment(BigDecimal.TEN, "amazon", card);
        final var request = RefundRequestBuilder.build(cardCreditTransaction.getAmount(), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(10.0)));

        await(2); // to handler async methods

        final var establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        final BankAccount userAccountUpdated = bankAccountRepository.findById(card.getAccountId()).get();
        assertThat(userAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(2);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldPartialRefundCardDebitTransaction() throws Exception {
        Card card = cardCreator.newCard();
        deposit("10", card.getAccountId());
        Transaction cardCreditTransaction = cardHelper.doDebitPayment(BigDecimal.TEN, "amazon", card);
        final var request = RefundRequestBuilder.build(new BigDecimal(5), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));

        await(2); // to handler async methods

        final BankAccount establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(2);
        final BankAccount userAccountUpdated = bankAccountRepository.findById(card.getAccountId()).get();
        assertThat(userAccountUpdated.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(2);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldDo2PartialRefundCardDebitTransaction() throws Exception {
        Card card = cardCreator.newCard();
        deposit("10", card.getAccountId());
        Transaction cardCreditTransaction = cardHelper.doDebitPayment(BigDecimal.TEN, "amazon", card);
        final var request = RefundRequestBuilder.build(new BigDecimal(5), "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));
        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.amount", equalTo(5)));

        await(2); // to handler async methods

        final BankAccount establishmentAccount = bankAccountRepository.findById("amazon").get();
        assertThat(establishmentAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(establishmentAccount.getId()).hasSize(3);
        assertThatStatementsFromAccount(establishmentAccount.getId()).hasSize(3);
        final BankAccount userAccountUpdated = bankAccountRepository.findById(card.getAccountId()).get();
        assertThat(userAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(card.getAccountId()).hasSize(3);
        assertThatStatementsFromAccount(card.getAccountId()).hasSize(3);
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenTryFullRefundDebitTransactionWithPartialRefunds() throws Exception {
        Card card = cardCreator.newCard();
        deposit("10", card.getAccountId());
        Transaction cardCreditTransaction = cardHelper.doDebitPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), 5);
        final var request = RefundRequestBuilder.build(BigDecimal.TEN, "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(REFUND_AMOUNT_HIGHER.getCode())));
    }

    @Test
    @WithEstablishmentUser(id = "amazon")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenDebitTransactionIsAlreadyRefundedAndTryFullRefund() throws Exception {
        Card card = cardCreator.newCard();
        deposit("10", card.getAccountId());
        Transaction cardCreditTransaction = cardHelper.doDebitPayment(BigDecimal.TEN, "amazon", card);
        doRefund(cardCreditTransaction.getId(), cardCreditTransaction.getAmount()); //full refund
        final var request = RefundRequestBuilder.build(10, "MD606");

        post(URL, cardCreditTransaction.getId(), "/refund", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(REFUND_AMOUNT_HIGHER.getCode())));
    }


    @Test
    @WithNormalUser
    void shouldReturn403WhenNormalUserTryToAccessRefundEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessRefundEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }

    void doRefund(String tId, BigDecimal amount) throws Exception {
        post(URL, tId, "/refund", RefundRequestBuilder.build(amount, "whatever"))
                .andExpect(status().isOk());
    }

    void doRefund(String tId, double amount) throws Exception {
        doRefund(tId, new BigDecimal(amount));
    }
}