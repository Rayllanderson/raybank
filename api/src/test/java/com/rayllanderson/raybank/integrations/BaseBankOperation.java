package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto;
import com.rayllanderson.raybank.dtos.requests.pix.PixPostDto;
import com.rayllanderson.raybank.dtos.responses.pix.PixPostResponse;
import com.rayllanderson.raybank.utils.BankDepositCreator;
import com.rayllanderson.raybank.utils.BankTransferCreator;
import com.rayllanderson.raybank.utils.PixCreator;

import java.math.BigDecimal;

public class BaseBankOperation extends BaseApiTest {

     void deposit(BigDecimal toDeposit){
        Long userId = authenticatedUserAccount.getUser().getId();
        BankDepositDto deposit = BankDepositCreator.createBankDepositDto(userId, toDeposit);
        post("/api/v1/users/authenticated/bank-account/deposit", deposit, Void.class);
    }

    protected void deposit300(){
        deposit(new BigDecimal("300.00"));
    }

    protected void deposit400(){
        deposit(new BigDecimal("400.00"));
    }

    protected void transfer400(){
        transfer(new BigDecimal("400.00"));
    }

    protected void transfer300(){
        transfer(new BigDecimal("300.00"));
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

    /**
     * @return Cartão de crédito do usuário autenticado
     */
    protected com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto getAuthCreditCard(){
        return get("/api/v1/users/authenticated/bank-account/credit-card",
                com.rayllanderson.raybank.dtos.responses.bank.CreditCardDto.class).getBody();
    }

    protected void payInvoice(BigDecimal value){
        var obj = CreditCardDto.builder().amount(value).account(authenticatedUserAccount).build();
        super.post("/api/v1/users/authenticated/bank-account/credit-card/pay/invoice", obj, Void.class);
    }

    /**
     * Realiza o pagamento da fatura no valor de 300 reais
     */
    protected void pay300Invoice(){
        var obj = CreditCardDto.builder().amount(new BigDecimal("300.00")).account(authenticatedUserAccount).build();
        super.post("/api/v1/users/authenticated/bank-account/credit-card/pay/invoice", obj, Void.class);
    }

    protected void buyWithCreditCard(BigDecimal value){
        var obj = CreditCardDto.builder().amount(value).account(authenticatedUserAccount).build();
        super.post("/api/v1/users/authenticated/payment/credit-card", obj, Void.class);
    }

    /**
     * Realiza uma compra de 500 reais com cartão de crédito
     */
    protected void buy500WithCreditCard(){
        buyWithCreditCard(new BigDecimal("500.00"));
    }

    /**
     * Key: rayllanderson@gmail.com
     */
    protected PixPostResponse registerAPix(){
        PixPostDto pixToBeSaved = PixCreator.createPixPixPostDto();
        return post("/api/v1/users/authenticated/pix", pixToBeSaved, PixPostResponse.class).getBody();
    }
}
