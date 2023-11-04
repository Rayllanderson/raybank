package com.rayllanderson.raybank.pix.controllers.pixreturn;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.PixReturnRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.PixKeyCreator;
import com.rayllanderson.raybank.e2e.helpers.PixHelper;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.service.transfer.PixTransferOutput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.utils.Await.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class PixReturnControllerTest extends E2eApiTest {

    @Autowired
    private PixKeyCreator pixKeyCreator;
    @Autowired
    private PixHelper pixHelper;
    private static final String URL = "/api/v1/internal/pix/return";

    @Test
    @WithNormalUser(id = "frieren")
    void shouldFullReturnPix() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        PixTransferOutput kaguyaPixTransfer = pixHelper.doTransfer(kaguyaKey, frierenKey, 10, "Toma ae");
        final var request = PixReturnRequestBuilder.build(kaguyaPixTransfer.getE2eId(), 10, "nao quero mais");

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.e2e_id", startsWith("D1397")))
                .andExpect(jsonPath("$.original_e2e_id", startsWith("E1397")))
                .andExpect(jsonPath("$.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.debit.name", equalTo(frierenKey.getName())))
                .andExpect(jsonPath("$.credit.name", equalTo(kaguyaKey.getName())))
                .andExpect(jsonPath("$.message", equalTo("nao quero mais")))
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.transaction_type", equalTo("PIX RETURN")));

        await(2); //to handler async methods

        final BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaKey.getAccountId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(kaguyaKey.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(kaguyaKey.getAccountId()).hasSize(2);
        final BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenKey.getAccountId()).get();
        assertThat(frierenAccountUpdated.getBalance()).isZero();
        assertThatTransactionsFromAccount(frierenKey.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(frierenKey.getAccountId()).hasSize(2);
    }

    @Test
    @WithNormalUser(id = "frieren")
    void shouldPartialReturnPix() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        PixTransferOutput kaguyaPixTransfer = pixHelper.doTransfer(kaguyaKey, frierenKey, 10, "Toma ae");
        final var request = PixReturnRequestBuilder.build(kaguyaPixTransfer.getE2eId(), 5, "nao quero mais");

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.e2e_id", startsWith("D1397")))
                .andExpect(jsonPath("$.original_e2e_id", startsWith("E1397")))
                .andExpect(jsonPath("$.amount", equalTo(5.0)))
                .andExpect(jsonPath("$.debit.name", equalTo(frierenKey.getName())))
                .andExpect(jsonPath("$.credit.name", equalTo(kaguyaKey.getName())))
                .andExpect(jsonPath("$.message", equalTo("nao quero mais")))
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.transaction_type", equalTo("PIX RETURN")));

        await(2); //to handler async methods

        final BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaKey.getAccountId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(kaguyaKey.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(kaguyaKey.getAccountId()).hasSize(2);
        final BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenKey.getAccountId()).get();
        assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("5.00"));
        assertThatTransactionsFromAccount(frierenKey.getAccountId()).hasSize(2);
        assertThatStatementsFromAccount(frierenKey.getAccountId()).hasSize(2);
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldThrowErrorWhenTryingToReturnSomeoneElsesPix() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        PixTransferOutput kaguyaPixTransfer = pixHelper.doTransfer(kaguyaKey, frierenKey, 10, "Toma ae");
        final var request = PixReturnRequestBuilder.build(kaguyaPixTransfer.getE2eId(), 10, "nao quero mais");

        post(URL, request)
                .andExpect(status().isForbidden());
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldThrowErrorWhenTryingToReturnNotExistingPix() throws Exception {
        final var request = PixReturnRequestBuilder.build("123", 10, "teste");

        post(URL, request)
                .andExpect(status().isForbidden());
    }


    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}