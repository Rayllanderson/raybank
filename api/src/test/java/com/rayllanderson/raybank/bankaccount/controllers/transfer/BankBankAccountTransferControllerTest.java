package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.BankAccountTransferRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.DEBIT_SAME_ACCOUNT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class BankBankAccountTransferControllerTest extends E2eApiTest {

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldTransferUsingAccount() throws Exception {
        BankAccount kaguyaAccount = accountCreator.newNormalBankAccountWithBalance("kaguya", BigDecimal.TEN);
        BankAccount frierenAccount = accountCreator.newNormalBankAccount("frieren");

        final var request = BankAccountTransferRequestBuilder.transferRequest(BigDecimal.TEN, "toma ae", frierenAccount.getNumber());

        post("/api/v1/internal/accounts", kaguyaAccount.getId(), "transfer", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.to", equalTo(frierenAccount.getNumber())))
                .andExpect(jsonPath("$.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.transaction.id", notNullValue()))
                .andExpect(jsonPath("$.transaction.type", equalTo("TRANSFER")));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        Assertions.assertThat(kaguyaAccountUpdated.getBalance()).isZero();
        final var kaguyaTransactions = transactionRepository.findAllByAccountId(kaguyaAccount.getId());
        assertThat(kaguyaTransactions).hasSize(1);
        final var kaguyaContacts = contactRepository.findAllByOwnerId(kaguyaAccount.getId());
        assertThat(kaguyaContacts).hasSize(1);
        BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenAccount.getId()).get();
        Assertions.assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        final var frierenTransactions = transactionRepository.findAllByAccountId(frierenAccount.getId());
        assertThat(frierenTransactions).hasSize(1);
        final var frierenContacts = contactRepository.findAllByOwnerId(frierenAccount.getId());
        assertThat(frierenContacts).isEmpty();
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldThrowErrorWhenTryTransferToSameAccount() throws Exception {
        BankAccount kaguyaAccount = accountCreator.newNormalBankAccountWithBalance("kaguya", BigDecimal.TEN);

        final var request = BankAccountTransferRequestBuilder.transferRequest(BigDecimal.TEN, "toma ae", kaguyaAccount.getNumber());

        post("/api/v1/internal/accounts", kaguyaAccount.getId(), "transfer", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(DEBIT_SAME_ACCOUNT.getCode())));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        Assertions.assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        final var transactions = transactionRepository.findAllByAccountId(kaguyaAccount.getId());
        assertThat(transactions).isEmpty();
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post("/api/v1/internal/accounts/123/transfer", null)
                .andExpect(status().isUnauthorized());
    }
}