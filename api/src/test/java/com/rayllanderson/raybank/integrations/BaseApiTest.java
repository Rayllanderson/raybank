package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.RaybankApplication;
import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.security.jwt.JwtUtil;
import com.rayllanderson.raybank.services.UserService;
import com.rayllanderson.raybank.utils.BankDepositCreator;
import com.rayllanderson.raybank.utils.BankTransferCreator;
import com.rayllanderson.raybank.utils.UserCreator;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.math.BigDecimal;

@Log4j2
@SpringBootTest(classes = RaybankApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BaseApiTest {

    @Autowired
    protected TestRestTemplate rest;

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("userDetailsService")
    protected UserDetailsService userDetailsService;

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
        userService.register(UserCreator.createUserToLogIn()); //registrando o usuário
        userService.register(UserCreator.createAnotherUserToLogIn()); //registrando usuário 2
        User user = (User) userDetailsService.loadUserByUsername(UserCreator.createUserToLogIn().getUsername());
        User secondUser =  (User) userDetailsService.loadUserByUsername(UserCreator.createAnotherUserToLogIn().getUsername());
        authenticatedUserAccount = user.getBankAccount();
        secondUserAccount = secondUser.getBankAccount();
        jwtToken = JwtUtil.createToken(user);
    }


    <T> ResponseEntity<T> post(String url, Object body, Class<T> responseType) {
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

    protected void deposit300(){
        deposit(new BigDecimal("400.00"));
    }

    protected void deposit400(){
        deposit(new BigDecimal("400.00"));
    }

    protected void transfer400(){
        transfer(new BigDecimal("300.00"));
    }

    protected void transfer300(){
        transfer(new BigDecimal("300.00"));
    }

    protected void deposit(BigDecimal toDeposit){
        Long userId = authenticatedUserAccount.getUser().getId();
        BankDepositDto deposit = BankDepositCreator.createBankDepositDto(userId, toDeposit);
        post("/api/v1/users/authenticated/bank-account/deposit", deposit, Void.class);
    }

    protected void transfer(BigDecimal toTransfer){
        BankTransferDto transaction = BankTransferCreator.createBankTransferDto(toTransfer, secondUserAccount.getAccountNumber().toString());
        post("/api/v1/users/authenticated/bank-account/transfer", transaction, Void.class);
    }

    /**
     * Vai depositar o primeiro parâmetro e em seguida irá transferir o segundo parâmetro
     */
    protected void depositAndTransfer(BigDecimal toDeposit, BigDecimal toTransfer){
        deposit(toDeposit);
        transfer(toTransfer);
    }
}