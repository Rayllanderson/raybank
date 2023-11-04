package com.rayllanderson.raybank.invoice.controllers.credit;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.helpers.CardHelper;
import com.rayllanderson.raybank.e2e.register.RegisterEstablishment;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.invoice.helper.InvoiceListHelper;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INVOICE_NOT_PAYABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class InvoiceCreditControllerTest extends E2eApiTest {

    @Autowired
    private CardCreator cardCreator;
    @Autowired
    private CardHelper cardHelper;
    private static final String URL = "/api/v1/internal/accounts/%s/cards/%s/invoices/%s/pay";

    @Test
    @WithNormalUser(id = "kaguya")
    @RegisterEstablishment(id = "amazon")
    void shouldCreditCurrentInvoice() throws Exception {
        final Card card = cardCreator.newCard("kaguya");
        deposit(10, "kaguya");
        cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        final InvoiceCreditRequest request = new InvoiceCreditRequest(BigDecimal.TEN);

        post(String.format(URL, card.getAccountId(), card.getId(), "current"), request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.payment.type", equalTo("PAYMENT")))
                .andExpect(jsonPath("$.payment.origin", equalTo("ACCOUNT")))
                .andExpect(jsonPath("$.destination.type", equalTo("INVOICE")))
                .andExpect(jsonPath("$.amount", equalTo(10.0)));

        BankAccount kaguyaAccount = bankAccountRepository.findById(card.getAccountId()).get();
        assertThat(kaguyaAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(3);
        assertThatStatementsFromAccount(kaguyaAccount.getId()).hasSize(3);
        List<Invoice> allInvoices = invoiceRepository.findAllByCard_Id(card.getId());
        assertThat(allInvoices).hasSize(1).doesNotContainNull();
        Invoice firstInvoice = allInvoices.stream().findFirst().get();
        assertThat(firstInvoice.getTotal()).isZero();
        assertThat(firstInvoice.getCredits()).hasSize(1);
        assertThat(firstInvoice.getInstallments()).hasSize(1);
    }

    @Test
    @WithNormalUser(id = "kaguya")
    @RegisterEstablishment(id = "amazon")
    void shouldCreditInvoiceById() throws Exception {
        final Card card = cardCreator.newCard("kaguya");
        deposit(10, "kaguya");
        cardHelper.doCreditPayment(BigDecimal.TEN, "amazon", card);
        final var cardInvoice = invoiceRepository.findAllByCard_Id(card.getId()).stream().findFirst().get();
        final InvoiceCreditRequest request = new InvoiceCreditRequest(BigDecimal.TEN);

        post(String.format(URL, card.getAccountId(), card.getId(), cardInvoice.getId()), request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.payment.type", equalTo("PAYMENT")))
                .andExpect(jsonPath("$.payment.origin", equalTo("ACCOUNT")))
                .andExpect(jsonPath("$.destination.type", equalTo("INVOICE")))
                .andExpect(jsonPath("$.amount", equalTo(10.0)));

        BankAccount kaguyaAccount = bankAccountRepository.findById(card.getAccountId()).get();
        assertThat(kaguyaAccount.getBalance()).isZero();
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(3);
        assertThatStatementsFromAccount(kaguyaAccount.getId()).hasSize(3);
        List<Invoice> allInvoices = invoiceRepository.findAllByCard_Id(card.getId());
        assertThat(allInvoices).hasSize(1).doesNotContainNull();
        Invoice firstInvoice = allInvoices.stream().findFirst().get();
        assertThat(firstInvoice.getTotal()).isZero();
        assertThat(firstInvoice.getCredits()).hasSize(1);
        assertThat(firstInvoice.getInstallments()).hasSize(1);
    }

    @Test
    @WithNormalUser(id = "kaguya")
    @RegisterEstablishment(id = "amazon")
    void shouldThrowErrorWhenTryCreditNotOpenInvoice() throws Exception {
        final Card card = cardCreator.newCard("kaguya");
        deposit(10, "kaguya");
        cardHelper.doCreditPayment(BigDecimal.TEN, 2, "amazon", card);
        final var cardInvoice = getSeccondInvoice(card.getId());
        final InvoiceCreditRequest request = new InvoiceCreditRequest(BigDecimal.TEN);

        post(String.format(URL, card.getAccountId(), card.getId(), cardInvoice.getId()), request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INVOICE_NOT_PAYABLE.getCode())));
    }

    private Invoice getSeccondInvoice(String cardId) {
        return new InvoiceListHelper(invoiceRepository.findAllByCard_Id(cardId)).getSortedInvoices().get(1);
    }

}