package com.rayllanderson.raybank.pix.controllers.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.PixPaymentRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.helpers.PixHelper;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class PixPaymentControllerTest extends E2eApiTest {

    @Autowired
    private PixHelper pixHelper;
    private static final String URL = "/api/v1/internal/pix/payment";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldPayUsingPixQrCode() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        final var qrCodeOutput = pixHelper.generateQrCode(BigDecimal.TEN, frierenKey.getKey(), "tomae");
        final var request = PixPaymentRequestBuilder.build(qrCodeOutput.getCode());

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.e2e_id", startsWith("E1397")))
                .andExpect(jsonPath("$.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.debit.name", equalTo(kaguyaKey.getName())))
                .andExpect(jsonPath("$.credit.name", equalTo(frierenKey.getName())))
                .andExpect(jsonPath("$.credit.key", equalTo(frierenKey.getKey())))
                .andExpect(jsonPath("$.message", equalTo("tomae")))
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.transaction_type", equalTo("PIX")));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaKey.getAccountId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isZero();
        final var kaguyaTransactions = transactionRepository.findAllByAccountId(kaguyaKey.getAccountId());
        assertThat(kaguyaTransactions).hasSize(1);
        BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenKey.getAccountId()).get();
        assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        final var frierenTransactions = transactionRepository.findAllByAccountId(frierenKey.getAccountId());
        assertThat(frierenTransactions).hasSize(1);
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}