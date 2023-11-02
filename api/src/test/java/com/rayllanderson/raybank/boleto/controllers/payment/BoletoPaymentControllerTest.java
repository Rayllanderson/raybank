package com.rayllanderson.raybank.boleto.controllers.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.BoletoBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.helpers.BoletoHelper;
import com.rayllanderson.raybank.e2e.helpers.CardHelper;
import com.rayllanderson.raybank.e2e.register.RegisterEstablishment;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.invoice.models.Invoice;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class BoletoPaymentControllerTest extends E2eApiTest {

    @Autowired
    private BoletoHelper boletoHelper;
    @Autowired
    private CardHelper cardHelper;
    @Autowired
    private CardCreator cardCreator;
    private static final String URL = "/api/v1/internal/boletos/payment";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldPayBoletoCreditingAnAccount() throws Exception {
        final BankAccount kaguyaAccount = accountCreator.newNormalBankAccountWithBalance("kaguya", "10");
        final BankAccount frierenAccount = accountCreator.newNormalBankAccount("frieren");
        final var boleto = boletoHelper.generateBoleto(BigDecimal.TEN, frierenAccount.getId(), BeneficiaryType.ACCOUNT, frierenAccount.getId());
        final var request = BoletoBuilder.buildRequest(boleto.getBarCode());

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.boleto.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.boleto.bar_code", equalTo(boleto.getBarCode())))
                .andExpect(jsonPath("$.type", equalTo("PAYMENT")));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isZero();
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(1);
        assertThatStatementsFromAccount(kaguyaAccountUpdated.getId()).hasSize(1);
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> { //to process boleto payment
            BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenAccount.getId()).get();
            assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
            assertThatTransactionsFromAccount(frierenAccountUpdated.getId()).hasSize(1);
            assertThatStatementsFromAccount(frierenAccountUpdated.getId()).hasSize(1);
        });
    }

    @Test
    @RegisterEstablishment(id = "Amazon")
    @WithNormalUser(id = "kaguya")
    void shouldPayBoletoCreditingAnInvoice() throws Exception {
        final BankAccount kaguyaAccount = accountCreator.newNormalBankAccountWithBalance("kaguya", "10");

        final Card frierenCard = cardCreator.newCard();
        cardHelper.doCreditPayment(BigDecimal.TEN, "Amazon", frierenCard); // para ter um valor em aberto na fatura
        final Invoice frierenInvoice = getCurrentInvoice(frierenCard.getId());
        final var boleto = boletoHelper.generateBoleto(BigDecimal.TEN, frierenInvoice.getId(), BeneficiaryType.INVOICE, frierenCard.getAccountId());
        final var request = BoletoBuilder.buildRequest(boleto.getBarCode());

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.boleto.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.boleto.bar_code", equalTo(boleto.getBarCode())))
                .andExpect(jsonPath("$.type", equalTo("PAYMENT")));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isZero();
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(1);
        assertThatStatementsFromAccount(kaguyaAccountUpdated.getId()).hasSize(1);
        //to process boleto payment
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            final Invoice frierenInvoiceUpdated = getCurrentInvoice(frierenCard.getId());
            assertThat(frierenInvoiceUpdated.getTotal()).isZero();
            assertThat(frierenInvoiceUpdated.getInstallments()).hasSize(1);
            assertThat(frierenInvoiceUpdated.getCredits()).hasSize(1);
            assertThatTransactionsFromAccount(frierenCard.getAccountId()).hasSize(2);
            assertThatStatementsFromAccount(frierenCard.getAccountId()).hasSize(2);
        });
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }

    private Invoice getCurrentInvoice(String cardId) {
        return invoiceRepository.findAllByCard_Id(cardId).stream().findFirst().get();
    }
}