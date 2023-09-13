package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.ContactResponseDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankStatementDto;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.BankStatementType;
import com.rayllanderson.raybank.utils.BankDepositCreator;
import com.rayllanderson.raybank.utils.BankTransferCreator;
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
class BankAccountControllerIT extends BaseBankOperation {

    final String API_URL = "/api/v1/users/authenticated/bank-account";

    @Test
    void findUserBankAccount_ReturnBankAccountDto_WhenSuccessful() {
        ResponseEntity<BankAccountDto> response = super.get(API_URL, BankAccountDto.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull();
        Assertions.assertThat(response.getBody().getId()).isNotNull();
        //numero da conta tem que ter 9 dígitos
        Assertions.assertThat(response.getBody().getAccountNumber().toString().length()).isEqualTo(9);
    }

    @Test
    void deposit_Returns204_WhenSuccessful() {
        var userId = super.authenticatedUserAccount.getUser().getId();
        BankDepositDto deposit = BankDepositCreator.createBankDepositDto(userId);

        BigDecimal expectedBalance = deposit.getAmount();

        ResponseEntity<Void> response = super.post(API_URL + "/deposit", deposit, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        BankAccountDto userAccount = getAuthAccount();

        Assertions.assertThat(userAccount).isNotNull();
        Assertions.assertThat(userAccount.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void deposit_Returns400_WhenDepositZero() {
        var userId = super.authenticatedUserAccount.getUser().getId();
        BankDepositDto deposit = BankDepositCreator.createAnInvalidBankDepositDto(userId);

        ResponseEntity<Void> response = super.post(API_URL + "/deposit", deposit, Void.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void transfer_Returns204_WhenUserHasMoney() {

        deposit400();

        BigDecimal toTransfer = BigDecimal.valueOf(300.00);

        BankTransferDto bankStatement = BankTransferCreator.createBankTransferDto(toTransfer,
                secondUserAccount.getAccountNumber().toString());
        ResponseEntity<Void> response = super.post(API_URL + "/transfer", bankStatement, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        var account = super.getAuthAccount();

        BigDecimal expectedBalance = new BigDecimal("400.00").subtract(toTransfer);

        Assertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void transfer_Returns400_WhenUserReceiverNotExists() {

        deposit400();

        BigDecimal toTransfer = BigDecimal.valueOf(300.00);

        BankTransferDto bankStatement = BankTransferCreator.createBankTransferDto(toTransfer, "hey, I'm not exist!");
        ResponseEntity<Void> response = super.post(API_URL + "/transfer", bankStatement, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        var account = super.getAuthAccount();

        BigDecimal expectedBalance = new BigDecimal("400.00");//garantindo que não transferiu

        Assertions.assertThat(account.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void transfer_Returns400_WhenUserHasNoMoney() {
        BankTransferDto bankStatement = BankTransferCreator.createBankTransferDto();
        ResponseEntity<Void> response = super.post(API_URL + "/transfer", bankStatement, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void transfer_Returns400_WhenUserTransferZero() {
        String receiverAccount = secondUserAccount.getAccountNumber().toString();
        BankTransferDto bankStatement = BankTransferCreator.createBankTransferDto(BigDecimal.ZERO, receiverAccount);
        ResponseEntity<Void> response = super.post(API_URL + "/transfer", bankStatement, Void.class);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void findAllStatements_ReturnListOfStatements_WhenSuccessful() {
        deposit400(); //gerou 1 bankStatement
        transfer300(); //gerou outro
        int expectedSize = 2;
        var expectedStatement = BankStatementDto.fromBankStatement(BankStatement.createDepositBankStatement(new BigDecimal("400"),
                authenticatedUserAccount));

        ResponseEntity<List<BankStatementDto>> response = rest.exchange(API_URL + "/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>() {
        });

        int deposit = 0;
        int transfer = 1;
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
        Assertions.assertThat(response.getBody().get(deposit).getBankStatementType()).isEqualTo(expectedStatement.getBankStatementType());
        Assertions.assertThat(response.getBody().get(transfer).getBankStatementType()).isEqualTo(BankStatementType.TRANSFER);
    }

    @Test
    void findAllStatements_ReturnEmptyList_WhenDidntMakeAnyBankStatement() {

        ResponseEntity<List<BankStatementDto>> response = rest.exchange(API_URL + "/statements", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isEmpty();
        Assertions.assertThat(response.getBody()).hasSize(0);
    }

    @Test
    void findAllContacts_ReturnsListOfContacts_WhenSuccessful() {
        var toDeposit = new BigDecimal("400.00");
        var toTransfer = new BigDecimal("300");
        //ao transferir, se o contato não estiver na lista, ele é adicionado
        depositAndTransfer(toDeposit, toTransfer);

        transfer300();
        int expectedSize = 1;
        ResponseEntity<List<ContactResponseDto>> response = rest.exchange(API_URL + "/contacts", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isNotEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
    }

    @Test
    void findAllContacts_ReturnsListEmpty_WhenUserHasNoContacts() {
        int expectedSize = 0;
        ResponseEntity<List<ContactResponseDto>> response = rest.exchange(API_URL + "/contacts", HttpMethod.GET,
                new HttpEntity<>(super.getHeaders()), new ParameterizedTypeReference<>() {
        });
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isNotNull().isEmpty();
        Assertions.assertThat(response.getBody()).hasSize(expectedSize);
    }
}