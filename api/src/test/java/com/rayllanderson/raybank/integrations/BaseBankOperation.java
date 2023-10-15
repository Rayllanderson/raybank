package com.rayllanderson.raybank.integrations;

import com.rayllanderson.raybank.bankaccount.controllers.requests.BankDepositDto;
import com.rayllanderson.raybank.bankaccount.controllers.requests.BankTransferDto;
import com.rayllanderson.raybank.card.controllers.external.PaymentCardRequest;
import com.rayllanderson.raybank.card.controllers.external.PaymentTypeRequest;
import com.rayllanderson.raybank.utils.BankDepositCreator;
import com.rayllanderson.raybank.utils.BankTransferCreator;
import com.rayllanderson.raybank.utils.PixCreator;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BaseBankOperation extends BaseApiTest {

     void deposit(BigDecimal toDeposit){
        var userId = authenticatedUserAccount.getUser().getId();
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
        BankTransferDto bankStatement = BankTransferCreator.createBankTransferDto(toTransfer, secondUserAccount.getAccountNumber().toString());
        post("/api/v1/users/authenticated/bank-account/transfer", bankStatement, Void.class);
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
//    protected com.rayllanderson.raybank.card.services.CreditCardDto getAuthCreditCard(){
//        return get("/api/v1/users/authenticated/bank-account/refund-card",
//                com.rayllanderson.raybank.card.services.CreditCardDto.class).getBody();
//    }

    protected void payInvoice(BigDecimal value){
//        var obj = CreditCardDto.builder().amount(value).account(authenticatedUserAccount).build();
//        super.post("/api/v1/users/authenticated/bank-account/refund-card/debit/invoice", obj, Void.class);
    }

    /**
     * Realiza o pagamento da fatura no valor de 300 reais
     */
    protected void pay300Invoice(){
//        var obj = CreditCardDto.builder().amount(new BigDecimal("300.00")).account(authenticatedUserAccount).build();
//        super.post("/api/v1/users/authenticated/bank-account/refund-card/debit/invoice", obj, Void.class);
    }

    protected void buyWithCreditCard(BigDecimal value){
        var payment = new PaymentCardRequest();
//                .amount(value)
//                .card(PaymentCardRequest.Card.builder()
//                        .number(authenticatedUserAccount.getCard().getNumber().toString())
//                        .securityCode(authenticatedUserAccount.getCard().getSecurityCode().toString())
//                        .expiryDate(authenticatedUserAccount.getCard().getExpiryDate()).build())
//                .paymentType(PaymentTypeRequest.CREDIT)
//                .installments(3)
//                .ocurredOn(LocalDateTime.now())
//                .description("Amazon")
//                .build();
        super.post("/api/v1/external/payments/card", payment, Void.class);
    }

    /**
     * Realiza uma compra de 500 reais com cartão de crédito
     */
    protected void buy500WithCreditCard(){
        buyWithCreditCard(new BigDecimal("500.00"));
    }
}
