package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.dtos.requests.bank.BankPaymentDto;
import com.rayllanderson.raybank.dtos.requests.bank.PaymentCrediCardDto;
import com.rayllanderson.raybank.dtos.requests.bank.PaymentTypeDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.time.YearMonth;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerIT extends BaseBankOperation {

    private final String API_URL = "/api/v1/users/authenticated/payment";

    @Test
    void pay_PayWithBoleto_WhenSuccessful() {
        deposit400();
        var toPay = new BigDecimal("300.00");
        var expectedBalance = new BigDecimal("400.00").subtract(toPay);
        BankPaymentDto payment = BankPaymentDto.builder().amount(toPay).build();
        ResponseEntity<Void> response = post(API_URL + "/boleto", payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        BankAccountDto authAccount = getAuthAccount();
        Assertions.assertThat(authAccount.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void pay_NotPayWithBoleto_WhenUserHasNoMoney() {
        BankAccountDto authAccount = getAuthAccount();
        var toPay = new BigDecimal("300.00");
        var expectedBalance = authAccount.getBalance();
        BankPaymentDto payment = BankPaymentDto.builder().amount(toPay).build();
        ResponseEntity<Void> response = post(API_URL + "/boleto", payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

        Assertions.assertThat(authAccount.getBalance()).isEqualTo(expectedBalance);
    }
}