package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.RaybankApplication;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.card.services.create.CreateCardService;
import com.rayllanderson.raybank.services.FindAllUsersService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@Log4j2
@SpringBootTest(classes = RaybankApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApiTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private FindAllUsersService userService;
    @Autowired
    private CreateCardService createCardService;

    protected String jwtToken = "";
    protected BankAccount authenticatedUserAccount;
    protected BankAccount secondUserAccount;

    HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
        return headers;
    }

    @BeforeEach
    public void setupTest() {
        log.info("Ta passando aqui 2x??");
//        userService.register(UserCreator.createUserToLogIn()); //registrando o usuário
//        userService.register(UserCreator.createAnotherUserToLogIn()); //registrando usuário 2
//        User user = (User) userDetailsService.loadUserByUsername(UserCreator.createUserToLogIn().getUsername());
//        User secondUser =  (User) userDetailsService.loadUserByUsername(UserCreator.createAnotherUserToLogIn().getUsername());
        User user = null;
        User secondUser = null;
        authenticatedUserAccount = user.getBankAccount();
//        authenticatedUserAccount.setCreditCard(creditCardService.createCreditCard(new CreateCreditCardInput(authenticatedUserAccount.getId(), BigDecimal.valueOf(5000), DueDays._6)));
        secondUserAccount = secondUser.getBankAccount();
    }


    protected <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = getHeaders();
        return rest.exchange(url, HttpMethod.POST, new HttpEntity<>(body, headers), responseType);
    }

    <T> ResponseEntity<T> get(String url, Class<T> responseType) {
        HttpHeaders headers = getHeaders();
        return rest.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), responseType);
    }

    <T> ResponseEntity<T> delete(String url, Class<T> responseType) {
        HttpHeaders headers = getHeaders();
        return rest.exchange(url, HttpMethod.DELETE, new HttpEntity<>(headers), responseType);
    }

    <T> ResponseEntity<T> put(String url, Object body, Class<T> responseType) {
        HttpHeaders headers = getHeaders();
        return rest.exchange(url, HttpMethod.PUT, new HttpEntity<>(body, headers), responseType);
    }

    protected BankAccountDto getAuthAccount(){
        return get("/api/v1/users/authenticated/bank-account", BankAccountDto.class).getBody();
    }
}