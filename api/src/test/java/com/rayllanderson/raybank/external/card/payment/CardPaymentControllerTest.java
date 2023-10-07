package com.rayllanderson.raybank.external.card.payment;

import com.rayllanderson.raybank.card.controllers.external.PaymentCardRequest;
import com.rayllanderson.raybank.card.controllers.external.PaymentTypeRequest;
import com.rayllanderson.raybank.integrations.BaseBankOperation;
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
class CardPaymentControllerTest extends BaseBankOperation {

    private static final String API_URL = "/api/v1/external/payments/card";

    @Test
    void pay_PayWithCreditCard_WhenSuccessful() {
        var toPay = new BigDecimal("300.00");
        var defaultCreditCardBalance = new BigDecimal("5000.00");
        var expectedBalance = defaultCreditCardBalance.subtract(toPay);
        final var userCreditCard = authenticatedUserAccount.getUser().getBankAccount().getCard();
        var payment = new PaymentCardRequest();
//                .amount(toPay)
//                .card(PaymentCardRequest.Card.builder()
//                        .number(userCreditCard.getNumber().toString())
//                        .securityCode(userCreditCard.getSecurityCode().toString())
//                        .expiryDate(userCreditCard.getExpiryDate()).build())
//                .paymentType(PaymentTypeRequest.CREDIT)
//                .build();

        ResponseEntity<Void> response = post(API_URL, payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

//        var creditCard = getAuthCreditCard();
//        Assertions.assertThat(creditCard.getBalance()).isEqualTo(expectedBalance);
//        Assertions.assertThat(creditCard.getInvoice()).isEqualTo(toPay);
    }

    @Test
    void pay_NotPayWithCreditCard_WhenCreditCardHasNoBalance() {
        var toPay = new BigDecimal("9999.00");
        var defaultCreditCardBalance = new BigDecimal("5000.00");
        final var userCreditCard = authenticatedUserAccount.getUser().getBankAccount().getCard();
        var payment = new PaymentCardRequest();
//                .amount(toPay)
//                .card(PaymentCardRequest.Card.builder()
//                        .number(userCreditCard.getNumber().toString())
//                        .securityCode(userCreditCard.getSecurityCode().toString())
//                        .expiryDate(userCreditCard.getExpiryDate()).build())
//                .paymentType(PaymentTypeRequest.CREDIT)
//                .build();

        ResponseEntity<Void> response = post(API_URL, payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);

//        var creditCard = getAuthCreditCard();
//        Assertions.assertThat(creditCard.getBalance()).isEqualTo(defaultCreditCardBalance);
    }

    @Test
    void shouldReturnBadRequestWhenCardNotExists() {
        var toPay = new BigDecimal("10.00");
        var defaultCreditCardBalance = new BigDecimal("5000.00");
        var payment = new PaymentCardRequest();
//                .builder()
//                .amount(toPay)
//                .card(PaymentCardRequest.Card.builder()
//                        .number("1111111111111111")
//                        .securityCode("123")
//                        .expiryDate(YearMonth.now()).build())
//                .paymentType(PaymentTypeRequest.CREDIT)
//                .build();

        ResponseEntity<Void> response = post(API_URL, payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//
//        var creditCard = getAuthCreditCard();
//        Assertions.assertThat(creditCard.getBalance()).isEqualTo(defaultCreditCardBalance);
    }

    @Test
    void shouldPayWithDebidCard() {
        var toPay = new BigDecimal("200.00");
        deposit300();
        final var userCreditCard = authenticatedUserAccount.getUser().getBankAccount().getCard();
        var payment = new PaymentCardRequest();
//                .amount(toPay)
//                .card(PaymentCardRequest.Card.builder()
//                        .number(userCreditCard.getNumber().toString())
//                        .securityCode(userCreditCard.getSecurityCode().toString())
//                        .expiryDate(userCreditCard.getExpiryDate()).build())
//                .paymentType(PaymentTypeRequest.CREDIT)
//                .build();

        ResponseEntity<Void> response = post(API_URL, payment, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        var creditCard = getAuthCreditCard();
        var account = getAuthAccount();
//        Assertions.assertThat(creditCard.getInvoice()).isZero();
        Assertions.assertThat(account.getBalance()).isEqualTo(new BigDecimal("100.00"));
    }
}