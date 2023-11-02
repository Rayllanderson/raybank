package com.rayllanderson.raybank.pix.controllers.transfer;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.PixTransferRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.utils.Await;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.DEBIT_SAME_ACCOUNT;
import static com.rayllanderson.raybank.utils.Await.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class PixTransferControllerTest extends E2eApiTest {

    public static final String URL = "/api/v1/internal/pix/transfer";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldTransferUsingPix() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final PixKey frierenKey = pixKeyCreator.newRandomKeyAndCreateAccount("frieren");
        final var request = PixTransferRequestBuilder.build(frierenKey.getKey(), BigDecimal.TEN, "tomae");

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.e2e_id", startsWith("E1397")))
                .andExpect(jsonPath("$.amount", equalTo(10)))
                .andExpect(jsonPath("$.debit.name", equalTo(kaguyaKey.getName())))
                .andExpect(jsonPath("$.credit.name", equalTo(frierenKey.getName())))
                .andExpect(jsonPath("$.credit.key", equalTo(frierenKey.getKey())))
                .andExpect(jsonPath("$.message", equalTo("tomae")))
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.transaction_type", equalTo("PIX")));



        Awaitility.await().atMost(10, TimeUnit.SECONDS).untilAsserted(() -> { //to handler AddContactAspect
            BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaKey.getAccountId()).get();
            assertThat(kaguyaAccountUpdated.getBalance()).isZero();
            final var kaguyaTransactions = transactionRepository.findAllByAccountId(kaguyaKey.getAccountId());
            assertThat(kaguyaTransactions).hasSize(1);
            final var kaguyaContacts = contactRepository.findAllByOwnerId(kaguyaKey.getAccountId());
            assertThat(kaguyaContacts).hasSize(1);
        });
        BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenKey.getAccountId()).get();
        assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        final var frierenTransactions = transactionRepository.findAllByAccountId(frierenKey.getAccountId());
        assertThat(frierenTransactions).hasSize(1);
        final var frierenContacts = contactRepository.findAllByOwnerId(frierenKey.getAccountId());
        assertThat(frierenContacts).isEmpty();
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldThrowErrorWhenTryTransferToSameAccount() throws Exception {
        final PixKey kaguyaKey = pixKeyCreator.newRandomKeyAndCreateAccountAndDeposit("kaguya", "10");
        final var request = PixTransferRequestBuilder.build(kaguyaKey.getKey(), BigDecimal.TEN, "tomae");

        post(URL, request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(DEBIT_SAME_ACCOUNT.getCode())));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaKey.getAccountId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        final var transactions = transactionRepository.findAllByAccountId(kaguyaKey.getAccountId());
        assertThat(transactions).isEmpty();
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}