package com.rayllanderson.raybank.bankaccount.controllers.transfer;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.builders.BankAccountTransferRequestBuilder;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
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
        assertThat(kaguyaAccountUpdated.getBalance()).isZero();
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(1);
        assertThatStatementsFromAccount(kaguyaAccount.getId()).hasSize(1);
        assertThatContactsFromAccount(kaguyaAccount.getId()).hasSize(1);
        BankAccount frierenAccountUpdated = bankAccountRepository.findById(frierenAccount.getId()).get();
        assertThat(frierenAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(frierenAccount.getId()).hasSize(1);
        assertThatStatementsFromAccount(frierenAccount.getId()).hasSize(1);
        assertThatContactsFromAccount(frierenAccount.getId()).isEmpty();
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldThrowErrorWhenTryTransferToSameAccount() throws Exception {
        BankAccount kaguyaAccount = accountCreator.newNormalBankAccountWithBalance("kaguya", BigDecimal.TEN);

        final var request = BankAccountTransferRequestBuilder.transferRequest(BigDecimal.TEN, "toma ae", kaguyaAccount.getNumber());

        post("/api/v1/internal/accounts", kaguyaAccount.getId(), "transfer", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.raybank_error.code", equalTo(DEBIT_SAME_ACCOUNT.getCode())));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).isEmpty();
        assertThatStatementsFromAccount(kaguyaAccount.getId()).isEmpty();
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post("/api/v1/internal/accounts/123/transfer", null)
                .andExpect(status().isUnauthorized());
    }
}