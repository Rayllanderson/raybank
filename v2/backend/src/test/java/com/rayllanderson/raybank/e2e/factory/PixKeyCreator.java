package com.rayllanderson.raybank.e2e.factory;

import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.model.key.PixKeyType;
import com.rayllanderson.raybank.pix.repositories.PixKeyRepository;
import com.rayllanderson.raybank.pix.service.key.register.RegisterPixKeyInput;
import com.rayllanderson.raybank.pix.service.key.register.RegisterPixKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PixKeyCreator {

    @Autowired
    private RegisterPixKeyService pixKeyService;
    @Autowired
    private BankAccountCreator bankAccountCreator;
    @Autowired
    private PixKeyRepository pixKeyRepository;

    public PixKey newKey(String key, PixKeyType type, String accountId) {
        String pixKey = pixKeyService.register(new RegisterPixKeyInput(key, type, accountId));
        return pixKeyRepository.findById(pixKey).get();
    }

    public PixKey newRandomKey(String accountId) {
        return newKey(null, PixKeyType.RANDOM, accountId);
    }

    public PixKey newRandomKeyAndCreateAccount(String accountId) {
        bankAccountCreator.newNormalBankAccount(accountId);
        return newKey(null, PixKeyType.RANDOM, accountId);
    }

    public PixKey newRandomKeyAndCreateAccountAndDeposit(String accountId, String balance) {
        bankAccountCreator.newNormalBankAccountWithBalance(accountId, balance);
        return newKey(null, PixKeyType.RANDOM, accountId);
    }
}
