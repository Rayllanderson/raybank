package com.rayllanderson.raybank.card.controllers.payment;

import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.bankaccount.model.BankAccountStatus;
import com.rayllanderson.raybank.card.models.Card;
import com.rayllanderson.raybank.e2e.E2ETest;
import com.rayllanderson.raybank.e2e.constants.Constants;
import com.rayllanderson.raybank.e2e.containers.postgres.E2eApiTest;
import com.rayllanderson.raybank.e2e.factory.CardCreator;
import com.rayllanderson.raybank.e2e.register.RegisterEstablishment;
import com.rayllanderson.raybank.e2e.security.WithEstablishmentUser;
import com.rayllanderson.raybank.e2e.security.WithNormalUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.math.BigDecimal;

import static com.rayllanderson.raybank.card.controllers.payment.CardPaymentRequestBuilder.cardPaymentRequest;
import static com.rayllanderson.raybank.card.controllers.payment.CardPaymentRequestBuilder.cardRequestFrom;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.ESTABLISHMENT_NOT_ACTIVE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSUFFICIENT_ACCOUNT_BALANCE;
import static com.rayllanderson.raybank.core.exceptions.RaybankExceptionReason.INSUFFICIENT_CARD_LIMIT;
import static com.rayllanderson.raybank.utils.Await.await;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@E2ETest
class CardPaymentControllerTest extends E2eApiTest {

    @Autowired
    private CardCreator cardCreator;

    @Test
    @WithEstablishmentUser
    @RegisterEstablishment
    void shouldPayWithCreditCard() throws Exception {
        Card savedCard = cardCreator.newCard();
        final var request = cardPaymentRequest(1000, "credit", 2, "Amazon", cardRequestFrom(savedCard));

        post("/api/v1/external/cards/payment", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.type", equalTo("CREDIT_CARD")))
                .andExpect(jsonPath("$.amount", equalTo("1000.00")));

        await(2); // to handler CardCreditPaymentCompletedEvent

        final var establishmentAccount = bankAccountRepository.findById(Constants.ESTABLISHMENT_ID).get();
        final var establishmentTransactions = transactionRepository.findAllByAccountId(establishmentAccount.getId());
        assertThat(establishmentAccount.getBalance()).isEqualTo(new BigDecimal("1000.00"));
        assertThat(establishmentTransactions).hasSize(1);
        final var userTransactions = transactionRepository.findAllByAccountId(savedCard.getAccountId());
        assertThat(userTransactions).hasSize(1);
        final var userInvoices = invoiceRepository.findAllByCard_Id(savedCard.getId());
        assertThat(userInvoices).hasSize(2);
        assertThat(userInvoices.get(0).getTotal()).isEqualTo(new BigDecimal("500.00"));
    }

    @Test
    @WithEstablishmentUser
    @RegisterEstablishment
    void shouldPayWithDebitCard() throws Exception {
        Card savedCard = cardCreator.newCard();
        accountHelper.deposit(1000, savedCard.getAccountId());
        final var request = cardPaymentRequest(1000, "debit", 1, "Amazon", cardRequestFrom(savedCard));

        post("/api/v1/external/cards/payment", request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transaction_id", notNullValue()))
                .andExpect(jsonPath("$.type", equalTo("DEBIT_CARD")))
                .andExpect(jsonPath("$.amount", equalTo("1000.00")));

        await(2); // to handler CardDebitPaymentCompletedEvent

        BankAccount establishmentAccount = bankAccountRepository.findById(Constants.ESTABLISHMENT_ID).get();
        final var establishmentTransactions = transactionRepository.findAllByAccountId(establishmentAccount.getId());
        assertThat(establishmentAccount.getBalance()).isEqualTo(new BigDecimal("1000.00"));
        assertThat(establishmentTransactions).hasSize(1);
        BankAccount userAccount = bankAccountRepository.findById(savedCard.getAccountId()).get();
        assertThat(userAccount.getBalance()).isZero();
        final var userTransactions = transactionRepository.findAllByAccountId(savedCard.getAccountId());
        assertThat(userTransactions).hasSize(1);
    }

    @Test
    @WithEstablishmentUser
    @RegisterEstablishment
    void shouldReturn422WhenUserHasNoCreditBalance() throws Exception {
        Card savedCard = cardCreator.newCard();
        final var request = cardPaymentRequest(100000000, "credit", 1, "Amazon", cardRequestFrom(savedCard));

        post("/api/v1/external/cards/payment", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSUFFICIENT_CARD_LIMIT.getCode())));

        BankAccount establishmentAccount = bankAccountRepository.findById(Constants.ESTABLISHMENT_ID).get();
        assertThat(establishmentAccount.getBalance()).isZero();
    }

    @Test
    @WithEstablishmentUser
    @RegisterEstablishment
    void shouldReturn422WhenUserHasNoDebitBalance() throws Exception {
        Card savedCard = cardCreator.newCard();
        final var request = cardPaymentRequest(100000000, "debit", 1, "Amazon", cardRequestFrom(savedCard));

        post("/api/v1/external/cards/payment", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(INSUFFICIENT_ACCOUNT_BALANCE.getCode())));

        BankAccount establishmentAccount = bankAccountRepository.findById(Constants.ESTABLISHMENT_ID).get();
        assertThat(establishmentAccount.getBalance()).isZero();
    }

    @Test
    @WithEstablishmentUser
    @RegisterEstablishment(status = BankAccountStatus.INACTIVE)
    void shouldReturn422WhenEstablishmentIsNotActive() throws Exception {
        Card savedCard = cardCreator.newCard();
        final var request = cardPaymentRequest(100000000, "debit", 1, "Amazon", cardRequestFrom(savedCard));

        post("/api/v1/external/cards/payment", request)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.ray_bank_error.code", equalTo(ESTABLISHMENT_NOT_ACTIVE.getCode())));

        BankAccount establishmentAccount = bankAccountRepository.findById(Constants.ESTABLISHMENT_ID).get();
        assertThat(establishmentAccount.getBalance()).isZero();
    }

    @Test
    @WithNormalUser
    void shouldReturn403WhenNormalUserTryToAccessPaymentEndpoint() throws Exception {

        post("/api/v1/external/cards/payment", null)
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    void shouldReturn401WhenAnonymousUserTryToAccessPaymentEndpoint() throws Exception {

        post("/api/v1/external/cards/payment", null)
                .andExpect(status().isUnauthorized());
    }

}