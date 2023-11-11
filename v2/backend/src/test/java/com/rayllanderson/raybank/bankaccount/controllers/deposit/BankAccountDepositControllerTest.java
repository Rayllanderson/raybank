package com.rayllanderson.raybank.bankaccount.controllers.deposit;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class BankAccountDepositControllerTest extends E2eApiTest {

    private static final String URL = "/api/v1/internal/accounts/deposit";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldDepositUsingAccount() throws Exception {
        BankAccount kaguyaAccount = accountCreator.newNormalBankAccount("kaguya");

        final var request = new BankAccountDepositRequest(BigDecimal.TEN);

        post(URL, request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount", equalTo(10.0)))
                .andExpect(jsonPath("$.transaction_id", notNullValue()));

        BankAccount kaguyaAccountUpdated = bankAccountRepository.findById(kaguyaAccount.getId()).get();
        assertThat(kaguyaAccountUpdated.getBalance()).isEqualTo(new BigDecimal("10.00"));
        assertThatTransactionsFromAccount(kaguyaAccount.getId()).hasSize(2);
        assertThatStatementsFromAccount(kaguyaAccount.getId()).hasSize(1);
    }


    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessEndpoint() throws Exception {

        post(URL, null)
                .andExpect(status().isUnauthorized());
    }
}