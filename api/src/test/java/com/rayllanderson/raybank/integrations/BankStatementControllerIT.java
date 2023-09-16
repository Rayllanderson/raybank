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
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BankStatementControllerIT extends BaseBankOperation{

    @Test
    void findAllStatements_ReturnsAllStatements_WhenSuccessful() {
        deposit400();
        deposit400();
        transfer300();
        buy500WithCreditCard();
        pay300Invoice();
        int expectedSize = 5;
        var response = rest.exchange("/api/v1/users/authenticated/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<List<BankStatementDto>>(){
        });

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
    }
}