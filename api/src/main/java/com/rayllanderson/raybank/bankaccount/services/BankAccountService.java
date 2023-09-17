package com.rayllanderson.raybank.bankaccount.services;

import com.rayllanderson.raybank.bankaccount.controllers.requests.BankDepositDto;
import com.rayllanderson.raybank.bankaccount.controllers.requests.BankPaymentDto;
import com.rayllanderson.raybank.bankaccount.controllers.requests.BankTransferDto;
import com.rayllanderson.raybank.bankaccount.controllers.reponses.BankAccountDto;
import com.rayllanderson.raybank.bankaccount.controllers.reponses.ContactResponseDto;
import com.rayllanderson.raybank.exceptions.BadRequestException;
import com.rayllanderson.raybank.bankaccount.model.BankAccount;
import com.rayllanderson.raybank.statement.models.BankStatement;
import com.rayllanderson.raybank.users.model.User;
import com.rayllanderson.raybank.statement.models.TransferStatement;
import com.rayllanderson.raybank.bankaccount.repository.BankAccountRepository;
import com.rayllanderson.raybank.statement.repository.BankStatementRepository;
import com.rayllanderson.raybank.users.services.find.UserFinderService;
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
    private final BankStatementRepository bankStatementRepository;
    private final UserFinderService userFinderService;

    /**
     * Cria e salva uma nova conta bancária junta de um cartão de crédito.
     *
     * @param savedUser usuário a receber nova conta. Este, por sua vez, necessita estar salvo no banco dados
     */
    @Transactional
    public BankAccount createAccountBank(User savedUser) {
        int accountNumber = this.generateAccountNumber();
        var bankAccountToBeSaved = BankAccount.builder()
                .accountNumber(accountNumber)
                .balance(BigDecimal.ZERO)
                .user(savedUser).build();
        bankAccountToBeSaved = bankAccountRepository.save(bankAccountToBeSaved);
        return bankAccountRepository.save(bankAccountToBeSaved);
    }

    /**
     * Realiza transferência de uma conta para outra.
     *
     * Sender precisa ser setado no objeto transferDto antes de ser enviado pra este método;
     *
     * Verifica se o sender possui saldo suficiente;
     *
     * @param transferDto objeto necessário para realização de transferência.
     * @throws BadRequestException Caso sender não esteja setado E caso o valor de transferência for maior que o saldo do sender
     */
    @Transactional
    public void transfer(BankTransferDto transferDto) throws BadRequestException{
        var senderId = transferDto.getSenderId();
        if(senderId == null) throw new BadRequestException("Sender must be set before send");
        BankAccount senderAccount = bankAccountRepository.findAccountWithContactsByUserId(senderId);
        BigDecimal amountToBeTransferred = transferDto.getAmount();
        if(senderAccount.hasAvailableBalance(amountToBeTransferred)) {
            User recipient = this.findUserByPixOrAccountNumber(transferDto);
            BankAccount recipientAccount = recipient.getBankAccount();
            if(senderAccount.equals(recipientAccount)) throw new BadRequestException("Você não pode transferir dinheiro pra você mesmo");
            senderAccount.transferTo(recipientAccount, amountToBeTransferred);

            //criando e salvando extratos
            TransferStatement recipientBankStatement = BankStatement.createTransferBankStatement(amountToBeTransferred,
                    senderAccount, recipientAccount, transferDto.getMessage());
            TransferStatement senderBankStatement = recipientBankStatement.invert();
            bankStatementRepository.saveAll(List.of(recipientBankStatement, senderBankStatement));

//            //adicionando extratos nas contas
//            recipientAccount.getBankStatements().add(recipientBankStatement);
//            senderAccount.getBankStatements().add(senderBankStatement);

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
    public void pay(BankPaymentDto paymentDto){
        var ownerId = paymentDto.getOwnerId();
        if(ownerId == null) throw new BadRequestException("Owner must be set before send");
        BankAccount bankAccount = bankAccountRepository.findAccountByUserId(ownerId);
        var amountToBePaid = paymentDto.getAmount();
        bankAccount.pay(amountToBePaid);
        BankStatement bankStatement = bankStatementRepository.save(BankStatement.createBoletoPaymentBankStatement(amountToBePaid, bankAccount));
//        bankAccount.getBankStatements().add(bankStatement);
        this.bankAccountRepository.save(bankAccount);
    }

    /**
     * Realiza o depósito na conta
     * @param depositDto objeto necessário para realização do depósito.
     * @throws BadRequestException Caso o owner não esteja setado
     */
    @Transactional
    public void deposit(BankDepositDto depositDto) throws BadRequestException{
        var ownerId = depositDto.getOwnerId();
        if(ownerId == null) throw new BadRequestException("Owner must be set before send");
        var ownerAccount = bankAccountRepository.findAccountByUserId(ownerId);
        var amountToBeDeposited = depositDto.getAmount();
        ownerAccount.deposit(amountToBeDeposited);
        BankStatement bankStatement = bankStatementRepository.save(BankStatement.createDepositBankStatement(amountToBeDeposited, ownerAccount));
//        ownerAccount.getBankStatements().add(bankStatement);
        bankAccountRepository.save(ownerAccount);
    }

    @Transactional(readOnly = true)
    public BankAccountDto findByUser(User user){
        return BankAccountDto.fromBankAccount(user.getBankAccount());
    }


    @Transactional(readOnly = true)
    public List<ContactResponseDto> findAllContactsUserId(String userId){
        var account = bankAccountRepository.findAccountWithContactsByUserId(userId);
        return account.getContacts().stream().map(ContactResponseDto::fromBankAccount).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ContactResponseDto findContactById(Long id, String userId){
        var accounts = this.findAllContactsUserId(userId);
        return accounts.stream().filter(contact -> contact.getId().equals(id)).findFirst()
                .orElseThrow(() -> new BadRequestException("Contato não encontrado"));
    }

    private User findUserByPixOrAccountNumber(BankTransferDto bankStatement) {
        String recipientPixKey = bankStatement.getTo();
        int recipientAccountNumber;
        try {
            recipientAccountNumber = Integer.parseInt(recipientPixKey);
        }catch (NumberFormatException e){
            recipientAccountNumber = -1;
        }
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
