package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.statement.controllers.BankStatementDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.math.BigDecimal;
import java.util.List;


@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FindCardControllerIT extends BaseBankOperation{

    String API_URL = "/api/v1/users/authenticated/bank-account/process-card";

    @Test
    void find(){
//        var response = get(API_URL, CreditCardDto.class);
//
//        var initialBalance = new BigDecimal("5000.00");
//        var initialInvoice = new BigDecimal("0.00");
//
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        Assertions.assertThat(response.getBody()).isNotNull();
//        Assertions.assertThat(response.getBody().getBalance()).isEqualTo(initialBalance);
//        Assertions.assertThat(response.getBody().getInvoice()).isEqualTo(initialInvoice);
    }

    @Test
    void findStatements_ReturnListOfStatements_WhenSuccessful(){
        buy500WithCreditCard(); //1
        buy500WithCreditCard(); //2
        int expectedSize = 2;
        ResponseEntity<List<BankStatementDto>> response = rest.exchange(API_URL + "/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>(){
                });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
    }

    @Test
    void findStatements_ReturnListOfCreditStatementsOnly_WhenSuccessful(){
        deposit300(); // ~1
        transfer400(); //~2
        transfer300(); //~3
        buy500WithCreditCard(); //1
        //Foram 4 transações! Teria que ser tamanho 4, mas ele precisa retornar 1, pois foi apenas uma transação feita com cartão
        int expectedSize = 1;
        ResponseEntity<List<BankStatementDto>> response = rest.exchange(API_URL + "/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>(){
                });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
    }

    @Test
    void findStatements_ReturnEmptyList_WhenUserDidntMakeAnyPurchase(){
        ResponseEntity<List<BankStatementDto>> response = rest.exchange(API_URL + "/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>(){
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isEmpty();
        Assertions.assertThat(response.getBody()).hasSize(0);
    }

    @Test
    void payInvoice_PayInvoice_WhenSuccessful(){
        buy500WithCreditCard();
        deposit300();
        var invoiceTotal = new BigDecimal("500.00");
        var amountToPay= new BigDecimal("300.00");
        var expectedNewInvoice= invoiceTotal.subtract(amountToPay);

//        var obj =
//                com.rayllanderson.raybank.card.controllers.CreditCardDto
//                        .builder().amount(amountToPay)
//                        .account(authenticatedUserAccount)
//                        .build();
//        ResponseEntity<Void> response = super.post("/api/v1/users/authenticated/bank-account/process-card/debit/invoice", obj, Void.class);

//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
//        CreditCardDto userCreditCard = getAuthCreditCard();
//        Assertions.assertThat(userCreditCard.getInvoice()).isEqualTo(expectedNewInvoice);
    }

    @Test
    void payInvoice_DoNotPayInvoice_WhenInvoiceIsZero(){
        deposit300();
        var amountToPay= new BigDecimal("300.00");

//        var obj =
//                com.rayllanderson.raybank.card.controllers.CreditCardDto
//                        .builder().amount(amountToPay)
//                        .account(authenticatedUserAccount)
//                        .build();
//        ResponseEntity<Void> response = super.post("/api/v1/users/authenticated/bank-account/process-card/debit/invoice", obj, Void.class);
//
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void payInvoice_NotPayInvoice_WhenUserHasNoMoney(){
        buy500WithCreditCard();
        var amountToPay= new BigDecimal("300.00");

//        var obj =
//                com.rayllanderson.raybank.card.controllers.CreditCardDto
//                        .builder().amount(amountToPay)
//                        .account(authenticatedUserAccount)
//                        .build();
//        ResponseEntity<Void> response = super.post("/api/v1/users/authenticated/bank-account/process-card/debit/invoice", obj, Void.class);
//
//        Assertions.assertThat(response).isNotNull();
//        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}