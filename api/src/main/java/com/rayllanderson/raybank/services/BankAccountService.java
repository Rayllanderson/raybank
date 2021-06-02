package com.rayllanderson.raybank.services;

import com.rayllanderson.raybank.dtos.requests.bank.BankDepositDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankPaymentDto;
import com.rayllanderson.raybank.dtos.requests.bank.BankTransferDto;
import com.rayllanderson.raybank.dtos.responses.bank.BankAccountDto;
import com.rayllanderson.raybank.dtos.responses.bank.ContactResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.models.BankAccount;
import com.rayllanderson.raybank.models.BankStatement;
import com.rayllanderson.raybank.models.User;
import com.rayllanderson.raybank.repositories.BankAccountRepository;
import com.rayllanderson.raybank.repositories.BankStatementRepository;
import com.rayllanderson.raybank.utils.NumberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;
    private final CreditCardService creditCardService;
    private final BankStatementRepository statementRepository;
    private final UserFinderService userFinderService;

    /**
     * Cria e salva uma nova conta bancária junta de um cartão de crédito.
     *
     * @param savedUser usuário a receber nova conta. Este, por sua vez, necessita estar salvo no banco dados
     * @return BankAccount salva com cartão de crédito
     */
    @Transactional
    public BankAccount createAccountBank(User savedUser) {
        int accountNumber = this.generateAccountNumber();
        var bankAccountToBeSaved = BankAccount.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .user(savedUser).build();
        bankAccountToBeSaved = bankAccountRepository.save(bankAccountToBeSaved);
        bankAccountToBeSaved.setCreditCard(creditCardService.createCreditCard(bankAccountToBeSaved));
        return bankAccountRepository.save(bankAccountToBeSaved);
    }

    /**
     * Realiza transferência de uma conta para outra.
     *
     * Sender precisa ser setado no objeto transaction antes de ser enviado pra este método;
     *
     * Verifica se o sender possui saldo suficiente;
     *
     * @param transaction objeto necessário para realização de transferência.
     * @throws BadRequestException Caso sender não esteja setado E caso o valor de transferência for maior que o saldo do sender
     */
    @Transactional
    public void transfer(BankTransferDto transaction) throws BadRequestException{
        if(transaction.getSender() == null) throw new BadRequestException("Sender must be set before send");
        BankAccount senderAccount = transaction.getSender().getBankAccount();
        BigDecimal amountToBeTransferred = transaction.getAmount();
        if(senderAccount.hasAvailableBalance(amountToBeTransferred)) {
            User recipient = this.findUserByPixOrAccountNumber(transaction);
            BankAccount recipientAccount = recipient.getBankAccount();
            if(senderAccount.equals(recipientAccount)) throw new BadRequestException("Você não pode transferir dinheiro pra você mesmo");
            senderAccount.transferTo(recipientAccount, amountToBeTransferred);

            //criando e salvando extratos
            BankStatement recipientStatement = statementRepository.save(BankStatement.createTransferStatement(amountToBeTransferred,
                    senderAccount, recipientAccount, transaction.getMessage()));
            BankStatement senderStatement = statementRepository.save(recipientStatement.toNegative());

            //adicionando extratos nas contas
            recipientAccount.getStatements().add(recipientStatement);
            senderAccount.getStatements().add(senderStatement);

            //adicionando ambos aos contatos
            senderAccount.addContact(recipientAccount);
            recipientAccount.addContact(senderAccount);

            //salvando
            bankAccountRepository.save(senderAccount);
            bankAccountRepository.save(recipientAccount);
        } else
            throw new BadRequestException("Valor da transferência é maior que o saldo bancário");
    }

    @Transactional
    public void pay(BankPaymentDto transaction){
        if(transaction.getOwner() == null) throw new BadRequestException("Owner must be set before send");
        BankAccount bankAccount = transaction.getOwner().getBankAccount();
        var amountToBePaid = transaction.getAmount();
        bankAccount.pay(amountToBePaid);
        BankStatement statement = statementRepository.save(BankStatement.createBoletoPaymentStatement(amountToBePaid, bankAccount));
        bankAccount.getStatements().add(statement);
        this.bankAccountRepository.save(bankAccount);
    }

    /**
     * Realiza o depósito na conta
     * @param transaction objeto necessário para realização do depósito.
     * @throws BadRequestException Caso o owner não esteja setado
     */
    @Transactional
    public void deposit(BankDepositDto transaction) throws BadRequestException{
        if(transaction.getOwner() == null) throw new BadRequestException("Owner must be set before send");
        var ownerAccount = transaction.getOwner().getBankAccount();
        var amountToBeDeposited = transaction.getAmount();
        ownerAccount.deposit(amountToBeDeposited);
        BankStatement statement = statementRepository.save(BankStatement.createDepositStatement(amountToBeDeposited, ownerAccount));
        ownerAccount.getStatements().add(statement);
        bankAccountRepository.save(ownerAccount);
    }

    @Transactional(readOnly = true)
    public BankAccountDto findByUser(User user){
        return BankAccountDto.fromBankAccount(user.getBankAccount());
    }


    @Transactional(readOnly = true)
    public List<ContactResponseDto> findAllContactsByAccount(BankAccount account){
        return account.getContacts().stream().map(ContactResponseDto::fromBankAccount).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContactResponseDto findContactById(Long id, BankAccount account){
        var accounts = this.findAllContactsByAccount(account);
        return accounts.stream().filter(contact -> contact.getId().equals(id)).findFirst()
                .orElseThrow(() -> new BadRequestException("Contato não encontrado"));
    }

    private User findUserByPixOrAccountNumber(BankTransferDto transaction){
        String recipientPixKey = transaction.getTo();
        int recipientAccountNumber = Integer.parseInt(recipientPixKey);
        return userFinderService.findByPixOrAccountNumber(recipientPixKey, recipientAccountNumber);
    }

    private int generateAccountNumber() {
        boolean isAccountNumberInvalid;
        int generatedNumber;
        final int NUMBER_OF_DIGITS = 9;
        do {
            generatedNumber = Integer.parseInt(Long.toString(NumberUtil.generateRandom(NUMBER_OF_DIGITS)));
            isAccountNumberInvalid = bankAccountRepository.existsByAccountNumber(generatedNumber)
                    && (Integer.toString(generatedNumber).length() != NUMBER_OF_DIGITS);
        } while (isAccountNumberInvalid);
        return generatedNumber;
    }
}
