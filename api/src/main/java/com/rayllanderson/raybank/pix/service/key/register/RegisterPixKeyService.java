package com.rayllanderson.raybank.pix.service.key.register;

import com.rayllanderson.raybank.core.exceptions.BadRequestException;
import com.rayllanderson.raybank.core.exceptions.UnprocessableEntityException;
import com.rayllanderson.raybank.pix.gateway.PixGateway;
import com.rayllanderson.raybank.pix.model.key.PixKey;
import com.rayllanderson.raybank.pix.model.key.PixKeyType;
import com.rayllanderson.raybank.pix.service.limit.generate.GeneratePixLimitService;
import com.rayllanderson.raybank.pix.util.PixKeyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterPixKeyService {

    private final PixGateway pixGateway;
    private final GeneratePixLimitService pixLimitService;
    protected static final int MAX_NUMBER_KEYS_PER_ACCOUNT = 5;

    public String register(final RegisterPixKeyInput keyInput) {
        String key = getKey(keyInput);
        PixKeyType keyType = keyInput.getType();

        boolean isKeyValid = keyType.isValid(key);
        if (!isKeyValid) {
            throw BadRequestException.formatted("Chave Pix %s não é valida", key);
        }

        validateNumberOfKeyInAccount(keyInput.getBankAccountId());

        if (pixGateway.existsByKey(key)) {
            throw UnprocessableEntityException.withFormatted("Chave Pix %s já cadastrada", key);
        }

        final PixKey pixKey = PixKey.from(key, keyType, keyInput.getBankAccountId());
        pixGateway.save(pixKey);

        createLimitIfNecessary(keyInput.getBankAccountId());

        return pixKey.getKey();
    }

    private void createLimitIfNecessary(String bankAccountId) {
        if(pixGateway.existsLimitByAccountId(bankAccountId)){
            return;
        }
        this.pixLimitService.generate(bankAccountId);
    }

    private static String getKey(final RegisterPixKeyInput keyInput) {
        final String key = keyInput.getKey();
        final PixKeyType keyType = keyInput.getType();

        if (PixKeyType.RANDOM.equals(keyType)) {
            return UUID.randomUUID().toString();
        }

        if (PixKeyType.CPF.equals(keyType) || PixKeyType.PHONE.equals(keyType)) {
            return PixKeyUtils.removeSpecialCharacters(key);
        }

        return key;
    }

    private void validateNumberOfKeyInAccount(String accountId) {
        int numberKeysInAccount = pixGateway.countKeysByAccountId(accountId);
        if (numberKeysInAccount == MAX_NUMBER_KEYS_PER_ACCOUNT) {
            throw UnprocessableEntityException.withFormatted("Número máximo (%s) de chaves excedido.", MAX_NUMBER_KEYS_PER_ACCOUNT);
        }
    }
}