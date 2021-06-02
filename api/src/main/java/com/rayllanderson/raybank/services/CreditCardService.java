package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.CreditCardDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.CreditCard;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import com.rayllanderson.raybank.repositories.CreditCardRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankStatementRepository statementRepository;

    /**
     * Cria e salva um novo cartão de crédito.
     *
     * @param savedBankAccount conta do banco a receber novo cartão de crédito.
     * Esta, por sua vez, necessita estar salvo no banco dados
     */
    @Transactional
    public CreditCard createCreditCard(BankAccount savedBankAccount){
        var creditCardToBeSaved = CreditCard.builder()
                .cardNumber(this.generateCreditCardNumber())
                .bankAccount(savedBankAccount)
                .balance(new BigDecimal(5000))
                .invoice(BigDecimal.ZERO)
                .build();
        return creditCardRepository.save(creditCardToBeSaved);
    }

    /**
     * Realiza um pagamento.
     * Verifica se tem limite;
     * Verifica se o valor da compra é maior que o saldo do cartão disponível
     * @param dto setar conta bancária antes de ser enviado.
     * @throws BadRequestException caso falhe em uma das verificações
     */
    @Transactional
    public void makePurchase(CreditCardDto dto) {
        if(dto.getAccount() == null)  throw new BadRequestException("Account must be set before send");
        CreditCard creditCard = findByAccountId(dto.getAccount().getId());
        creditCard.makePurchase(dto.getAmount());
        var statement = statementRepository.save(BankStatement.createCreditPurchaseStatement(
                dto.getAmount(), creditCard.getBankAccount()
        ));
        creditCard.getStatements().add(statement);
        creditCardRepository.save(creditCard);
    }

    /**
     * Realiza o pagamento da fatura.
     * Verifica se existe fatura;
     * Verifica se o valor da compra é maior que o valor da fatura.
     * Case o valor seja maior, é realizado um reembolso da diferença do valor (valor do pagamento - fatura)
     * então é realizado o pagamento da fatura e o saldo bancário com o reembolso é atualizado
     * @param dto setar conta bancária antes de ser enviado.
     * @throws BadRequestException caso o cartão não tenha faturas
     */
    @Transactional
    public void payInvoice(CreditCardDto dto) {
        if(dto.getAccount() == null)  throw new BadRequestException("Account must be set before send");
        CreditCard creditCard = findByAccountId(dto.getAccount().getId());
        try {
            creditCard.payTheInvoice(dto.getAmount());
        } catch (IllegalArgumentException e){
            creditCard.payInvoiceAndRefundRemaining(dto.getAmount());
        }finally {
            var statement = statementRepository.save(BankStatement.createInvoicePaymentStatement(
                    dto.getAmount(), creditCard.getBankAccount()
            ));
            creditCard.getStatements().add(statement);
            bankAccountRepository.save(creditCard.getBankAccount());
            creditCardRepository.save(creditCard);
        }
    }

    @Transactional(readOnly = true)
    public CreditCard findByAccountId(Long accountId){
        return creditCardRepository.findByBankAccountId(accountId).orElseThrow(()-> new BadRequestException("Este cartão de crédito não existe"));
    }

    private long generateCreditCardNumber() {
        boolean isCardNumberInvalid;
        long generatedNumber;
        final int NUMBER_OF_DIGITS = 16;
        do {
            generatedNumber = NumberUtil.generateRandom(NUMBER_OF_DIGITS);
            isCardNumberInvalid =
                    creditCardRepository.existsByCardNumber(generatedNumber) && (Long.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isCardNumberInvalid);
        return generatedNumber;
    }
}
