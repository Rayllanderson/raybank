package com.rayllanderson.raybank.boleto.controllers.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.boleto.models.BeneficiaryType;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.BoletoBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.helpers.BoletoHelper;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
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
    private static final String URL = "/api/v1/internal/boletos/payment";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldPayboleto() throws Exception {
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
        final var kaguyaTransactions = transactionRepository.findAllByAccountId(kaguyaAccount.getId());
        assertThat(kaguyaTransactions).hasSize(1);
        //to process boleto payment
        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> {
            BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenAccount.getId()).get();
            assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
            final var frierenTransactions = transactionRepository.findAllByAccountId(frierenAccount.getId());
            assertThat(frierenTransactions).hasSize(1);
        });
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}