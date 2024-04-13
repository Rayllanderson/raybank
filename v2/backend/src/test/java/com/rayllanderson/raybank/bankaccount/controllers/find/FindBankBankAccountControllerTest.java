package com.rayllanderson.raybank.bankaccount.controllers.find;

import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class FindBankBankAccountControllerTest extends E2eApiTest {

    @Autowired
    private CardCreator cardCreator;
    private static final String URL = "/api/v1/internal/accounts/authenticated";

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldFindAccountDetailsWithoutCard() throws Exception {
        accountCreator.newNormalBankAccount("kaguya");

        get(URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name", equalTo("kaguya")))
                .andExpect(jsonPath("$.user.type", equalTo("USER")))
                .andExpect(jsonPath("$.account.id", equalTo("kaguya")))
                .andExpect(jsonPath("$.account.card", nullValue()))
                .andExpect(jsonPath("$.account.status", equalTo("ACTIVE")));
    }

    @Test
    @WithNormalUser(id = "kaguya")
    void shouldFindAccountDetailsWithCard() throws Exception {
        accountCreator.newNormalBankAccount("kaguya");
        cardCreator.newCard("kaguya");

        get(URL)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.name", equalTo("kaguya")))
                .andExpect(jsonPath("$.user.type", equalTo("USER")))
                .andExpect(jsonPath("$.account.id", equalTo("kaguya")))
                .andExpect(jsonPath("$.account.card", notNullValue()))
                .andExpect(jsonPath("$.account.card.id", notNullValue()))
                .andExpect(jsonPath("$.account.status", equalTo("ACTIVE")));
    }
}